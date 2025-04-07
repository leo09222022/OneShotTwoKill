package totalsalesproduct.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.gui.AdminMainGUI;
import main.gui.MainGUI;
import totalsalesproduct.database.TotalSalesProductDAO;
import totalsalesproduct.database.TotalSalesProductVO;

public class TotalSalesProductGUI extends JFrame {
    JTextField jtf;
    JLabel jlabel;
    JComboBox<String> yearCombo, monthCombo, dayCombo; 
    Vector<String> colNames = new Vector<String>();
    Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
    private JScrollPane jsp;
    private JTable jtb;
    String[] sArr = {"바코드", "상품명", "판매량", "원가", "판매가", "총수익", "순이익", "이익률"};
    ArrayList<TotalSalesProductVO> list = new ArrayList<TotalSalesProductVO>();
    TotalSalesProductVO vo = new TotalSalesProductVO();
    TotalSalesProductDAO dao = new TotalSalesProductDAO();
    
    // 추가: 날짜 입력 컴포넌트를 제어하기 위한 패널
    private JPanel p_date_panel;
    private JLabel yearLabel, monthLabel, dayLabel;
    private JButton searchBtn;
    
    public TotalSalesProductGUI() {
        setTitle("매출관리");
        
        // 전체 레이아웃 설정
        setLayout(new BorderLayout());
        
        // 패널 생성
        JPanel p_top = new JPanel();
        JPanel p_center = new JPanel();
        JPanel p_center_top = new JPanel();
        p_date_panel = new JPanel(); // 패널 클래스 멤버로 변경
        JPanel p_button_panel = new JPanel();
        JPanel p_center_mid = new JPanel();
        JPanel p_south = new JPanel();
        
        // 패널 배경색 설정
        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        p_center_top.setBackground(Color.WHITE);
        p_date_panel.setBackground(Color.WHITE);
        p_button_panel.setBackground(Color.WHITE);
        p_center_mid.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        
        // 패널 레이아웃 설정
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_center.setLayout(new BorderLayout());
        p_center_top.setLayout(new BoxLayout(p_center_top, BoxLayout.Y_AXIS));
        p_date_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_button_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_center_mid.setLayout(new BorderLayout());
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        // 상단 패널: 뒤로가기 버튼
        JButton btnBack = new JButton("< 뒤로가기");
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        p_top.add(btnBack);
        
        // 뒤로가기 버튼 이벤트 처리
        btnBack.addActionListener(e -> {
            dispose();
            new AdminMainGUI();
        });
        
        // 하단 영역 : 공통 버튼( 메인 화면으로 이동)
        JButton btnExit = new JButton("메인으로 이동");
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_south.add(btnExit);
        add(p_south,BorderLayout.SOUTH);
        btnExit.setBorderPainted(false);
        btnExit.setBackground(Color.WHITE);
        btnExit.setForeground(Color.BLACK);
        btnExit.setFocusPainted(false);
        
        btnExit.addActionListener(e -> {
            dispose();
            new MainGUI();
        });
        
        // 날짜 레이블 초기화
        yearLabel = new JLabel("년");
        monthLabel = new JLabel("월");
        dayLabel = new JLabel("일");
        
        // 현재 날짜 가져오기
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        
        // 연도 ComboBox 설정 - 현재 연도부터 5년 전까지
        Vector<String> years = new Vector<>();
        for (int year = currentYear; year >= currentYear - 5; year--) {
            years.add(String.valueOf(year));
        }
        yearCombo = new JComboBox<>(years);
        yearCombo.setPreferredSize(new Dimension(80, 25));
        
        // 월 ComboBox 설정
        Vector<String> months = new Vector<>();
        for (int month = 1; month <= 12; month++) {
            months.add(String.format("%02d", month));
        }
        monthCombo = new JComboBox<>(months);
        monthCombo.setPreferredSize(new Dimension(60, 25));
        
        // 일 ComboBox 설정
        updateDayCombo(currentYear, currentMonth);
        
        // 현재 날짜로 기본값 설정
        yearCombo.setSelectedItem(String.valueOf(currentYear));
        monthCombo.setSelectedItem(String.format("%02d", currentMonth));
        dayCombo.setSelectedItem(String.format("%02d", currentDay));
        
        // 연도나 월이 변경되면 일(day) ComboBox 업데이트
        yearCombo.addActionListener(e -> {
            updateDayCombo(
                Integer.parseInt((String)yearCombo.getSelectedItem()),
                Integer.parseInt((String)monthCombo.getSelectedItem())
            );
        });
        
        monthCombo.addActionListener(e -> {
            updateDayCombo(
                Integer.parseInt((String)yearCombo.getSelectedItem()),
                Integer.parseInt((String)monthCombo.getSelectedItem())
            );
        });
        
        // 검색 버튼 추가
        searchBtn = new JButton("검색");
        styleGreenButton(searchBtn);
        
        // 버튼 생성 - 전체 버튼을 년별 버튼으로 변경
        JButton year_btn = new JButton("년별");  // 전체 -> 년별로 변경
        JButton month_btn = new JButton("월별");
        JButton day_btn = new JButton("일별");
        
        // 버튼 스타일링
        styleGreenButton(year_btn);
        styleGreenButton(month_btn);
        styleGreenButton(day_btn);
        
        // 버튼 패널에 버튼 추가
        p_button_panel.add(year_btn);
        p_button_panel.add(month_btn);
        p_button_panel.add(day_btn);
        
        // 기본적으로 날짜 입력 패널은 비어있음 (초기 상태에는 날짜 입력 필드가 보이지 않음)
        
        // 중앙 상단 패널에 날짜 패널과 버튼 패널 추가
        p_center_top.add(p_date_panel);
        p_center_top.add(p_button_panel);
        
        // 두 패널 사이에 약간의 여백 추가
        p_date_panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        p_button_panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // 컬럼명 추가
        for(String s : sArr) {
            colNames.add(s);
        }
        
        // 테이블 모델 생성
        DefaultTableModel model = new DefaultTableModel(rowData, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 2 && columnIndex <= 6) {
                    return Integer.class;
                } else if (columnIndex == 7) {
                    return Double.class;
                }
                return String.class;
            }
        };
        
        // 테이블 생성 및 설정
        jtb = new JTable(model);
        jtb.getTableHeader().setReorderingAllowed(false);
        jtb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        // 각 열의 너비 설정
        jtb.getColumnModel().getColumn(0).setPreferredWidth(70);
        jtb.getColumnModel().getColumn(1).setPreferredWidth(150);
        jtb.getColumnModel().getColumn(2).setPreferredWidth(70);
        jtb.getColumnModel().getColumn(3).setPreferredWidth(70);
        jtb.getColumnModel().getColumn(4).setPreferredWidth(70);
        jtb.getColumnModel().getColumn(5).setPreferredWidth(100);
        jtb.getColumnModel().getColumn(6).setPreferredWidth(100);
        jtb.getColumnModel().getColumn(7).setPreferredWidth(70);
        
        // 이익률 열에 특별한 렌더러 적용 (소수점 두 자리 + % 표시)
        DefaultTableCellRenderer percentRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component comp = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                if (value instanceof Double) {
                    double doubleValue = (Double) value;
                    setText(String.format("%.2f%%", doubleValue));
                }
                
                return comp;
            }
        };
        percentRenderer.setHorizontalAlignment(JLabel.RIGHT);
        jtb.getColumnModel().getColumn(7).setCellRenderer(percentRenderer);
        
        // 숫자 열에 오른쪽 정렬 렌더러 적용
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        for (int i = 2; i <= 6; i++) {
            jtb.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
        
        // 스크롤 패널에 테이블 추가
        jsp = new JScrollPane(jtb);
        jsp.setPreferredSize(new Dimension(350, 380));
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        // 중앙 중간 패널에 스크롤 패널 추가
        p_center_mid.add(jsp, BorderLayout.CENTER);
        
        // 중앙 패널에 상단 및 중간 패널 추가
        p_center.add(p_center_top, BorderLayout.NORTH);
        p_center.add(p_center_mid, BorderLayout.CENTER);
        
        // 프레임에 패널 추가
        add(p_top, BorderLayout.NORTH);
        add(p_center, BorderLayout.CENTER);
        add(p_south, BorderLayout.SOUTH);
        
        // 버튼 이벤트 처리
        year_btn.addActionListener(e -> {
            // 년별 버튼 클릭 시, 연도만 선택할 수 있게 설정
            updateDatePanel(true, false, false);
        });
        
        month_btn.addActionListener(e -> {
            // 월별 버튼 클릭 시, 연도와 월 선택할 수 있게 설정
            updateDatePanel(true, true, false);
        });
        
        day_btn.addActionListener(e -> {
            // 일별 버튼 클릭 시, 연도, 월, 일 모두 선택할 수 있게 설정
            updateDatePanel(true, true, true);
        });
        
        searchBtn.addActionListener(e -> {
            try {
                // 현재 어떤 날짜 필드가 표시되어 있는지 확인
                boolean yearVisible = p_date_panel.isAncestorOf(yearCombo);
                boolean monthVisible = p_date_panel.isAncestorOf(monthCombo);
                boolean dayVisible = p_date_panel.isAncestorOf(dayCombo);
                
                String year = null;
                String month = null;
                String day = null;
                
                // 표시된 필드의 값만 가져옴
                if (yearVisible) {
                    year = (String) yearCombo.getSelectedItem();
                }
                
                if (monthVisible) {
                    month = (String) monthCombo.getSelectedItem();
                }
                
                if (dayVisible) {
                    day = (String) dayCombo.getSelectedItem();
                }
                
                // 테이블 초기화
                clearTable();
                
                // 어떤 검색을 할지 결정 (년별, 월별, 일별)
                if (dayVisible) {
                    // 일별 검색
                    System.out.println("일별 검색: " + year + "-" + month + "-" + day);
                    list = dao.selectDailySalesProduct(year, month, day);
                    
                    if (list != null && !list.isEmpty()) {
                        addDataToTable(list);
                        System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
                    } else {
                        System.out.println("해당 일에 데이터가 없습니다: " + year + "년 " + month + "월 " + day + "일");
                    }
                } else if (monthVisible) {
                    // 월별 검색
                    System.out.println("월별 검색: " + year + "-" + month);
                    list = dao.selectMonthlySalesProduct(year, month);
                    
                    if (list != null && !list.isEmpty()) {
                        addDataToTable(list);
                        System.out.println("월별 데이터 로드 완료: " + year + "년 " + month + "월, " + list.size() + "개의 행");
                    } else {
                        System.out.println("해당 월에 데이터가 없습니다: " + year + "년 " + month + "월");
                    }
                } else if (yearVisible) {
                    // 년별 검색 (새 기능)
                    System.out.println("연도별 검색: " + year);
                    list = dao.selectYearlySalesProduct(year); // 이 메서드를 DAO에 추가해야 함
                    
                    if (list != null && !list.isEmpty()) {
                        addDataToTable(list);
                        System.out.println("연도별 데이터 로드 완료: " + year + "년, " + list.size() + "개의 행");
                    } else {
                        System.out.println("해당 연도에 데이터가 없습니다: " + year + "년");
                    }
                }
            } catch (Exception ex) {
                System.out.println("데이터 조회 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        // 초기 데이터 로드
        try {
            list = dao.selectAllSalesProduct();
            if (list != null && !list.isEmpty()) {
                addDataToTable(list);
            }
        } catch (Exception ex) {
            System.out.println("초기 데이터 로드 중 오류 발생: " + ex.getMessage());
        }
        
        // 프레임 설정
        setTitle("무인편의점 키오스크");
        setSize(375, 660);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // 날짜 패널 업데이트 메서드 (표시할 필드 설정)
    private void updateDatePanel(boolean showYear, boolean showMonth, boolean showDay) {
        // 패널 초기화
        p_date_panel.removeAll();
        
        // 지정된 컴포넌트만 추가
        if (showYear) {
            p_date_panel.add(yearCombo);
            p_date_panel.add(yearLabel);
        }
        
        if (showMonth) {
            p_date_panel.add(monthCombo);
            p_date_panel.add(monthLabel);
        }
        
        if (showDay) {
            p_date_panel.add(dayCombo);
            p_date_panel.add(dayLabel);
        }
        
        // 어떤 필드라도 표시되면 검색 버튼 추가
        if (showYear || showMonth || showDay) {
            p_date_panel.add(searchBtn);
        }
        
        // 패널 갱신
        p_date_panel.revalidate();
        p_date_panel.repaint();
    }
    
    // 연/월에 따라 일(day) ComboBox를 업데이트하는 메서드
    private void updateDayCombo(int year, int month) {
        String selectedDay = null;
        if (dayCombo != null && dayCombo.getSelectedItem() != null) {
            selectedDay = (String) dayCombo.getSelectedItem();
        }
        
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        Vector<String> days = new Vector<>();
        for (int day = 1; day <= lastDay; day++) {
            days.add(String.format("%02d", day));
        }
        
        if (dayCombo == null) {
            dayCombo = new JComboBox<>(days);
            dayCombo.setPreferredSize(new Dimension(60, 25));
        } else {
            dayCombo.removeAllItems();
            for (String day : days) {
                dayCombo.addItem(day);
            }
        }
        
        if (selectedDay != null) {
            int day = Integer.parseInt(selectedDay);
            if (day <= lastDay) {
                dayCombo.setSelectedItem(selectedDay);
            } else {
                dayCombo.setSelectedItem(String.format("%02d", lastDay));
            }
        }
    }
    
    // 테이블의 모든 데이터를 지우는 메서드
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) jtb.getModel();
        model.setRowCount(0);
        list.clear();
        rowData.clear();
    }
    
    // 테이블에 데이터 추가하는 메서드
    private void addDataToTable(ArrayList<TotalSalesProductVO> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            System.out.println("표시할 데이터가 없습니다.");
            return;
        }
        
        for (TotalSalesProductVO v : dataList) {
            Vector<Object> row = new Vector<Object>();
            row.add(v.getProductId());
            row.add(v.getProductName());
            row.add(v.getSalesCount());
            row.add(v.getCostPriceAt());
            row.add(v.getSalePriceAt());
            row.add(v.getSumSalePrice());
            row.add(v.getProfits());
            row.add(v.getProfitsRate());
            
            ((DefaultTableModel)jtb.getModel()).addRow(row);
        }
    }
    
    // 버튼 스타일링 메서드
    private void styleGreenButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(new Color(30, 135, 61));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
    }
}