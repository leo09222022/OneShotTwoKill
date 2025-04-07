package totalordersproduct.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox; // JComboBox 추가
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.gui.MainGUI;
import main.gui.ProductManagementGUI;
import totalordersproduct.database.TotalOrdersProductDAO;
import totalordersproduct.database.TotalOrdersProductVO;

public class TotalOrdersProductGUI extends JFrame {
    JTextField jtf;
    JLabel jlabel;
    // JTextField를 JComboBox로 변경
    JComboBox<String> yearCombo, monthCombo, dayCombo;
    Vector<String> colNames = new Vector<String>();
    Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
    private JScrollPane jsp;
    private JTable jtb;
    String[] sArr = {"바코드", "상품명", "발주수량", "발주일", "원가", "총가격", "비고사항"};
    ArrayList<TotalOrdersProductVO> list = new ArrayList<TotalOrdersProductVO>();
    TotalOrdersProductVO vo = new TotalOrdersProductVO();
    TotalOrdersProductDAO dao = new TotalOrdersProductDAO();
    JLabel jlb;

    public TotalOrdersProductGUI() {
        setTitle("발주 내역");
        
        // 전체 레이아웃 설정
        setLayout(new BorderLayout());
        
        // 패널 생성
        JPanel p_top = new JPanel();
        JPanel p_center = new JPanel();
        JPanel p_center_top = new JPanel();
        JPanel p_date_panel = new JPanel();
        JPanel p_button_panel = new JPanel();
        JPanel p_total_panel = new JPanel();
        JPanel p_center_mid = new JPanel();
        JPanel p_south = new JPanel();
        
        // 패널 배경색 설정
        p_top.setBackground(Color.WHITE);
        p_center.setBackground(Color.WHITE);
        p_center_top.setBackground(Color.WHITE);
        p_date_panel.setBackground(Color.WHITE);
        p_button_panel.setBackground(Color.WHITE);
        p_total_panel.setBackground(Color.WHITE);
        p_center_mid.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        
        // 패널 레이아웃 설정
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_center.setLayout(new BorderLayout());
        p_center_top.setLayout(new BoxLayout(p_center_top, BoxLayout.Y_AXIS));
        p_date_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_button_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_total_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
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
            new ProductManagementGUI();
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
        
        // 날짜 입력 패널: 연/월/일 ComboBox와 레이블
        JLabel yearLabel = new JLabel("년");
        JLabel monthLabel = new JLabel("월");
        JLabel dayLabel = new JLabel("일");
        
        // 현재 날짜 가져오기
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        
        // 연도 ComboBox 설정 - 현재 연도부터 5년 전까지
        Vector<String> years = new Vector<>();
        years.add(String.valueOf(2025));
        years.add(String.valueOf(2024));
        yearCombo = new JComboBox<>(years);
        yearCombo.setPreferredSize(new Dimension(80, 25));
        
        // 월 ComboBox 설정
        Vector<String> months = new Vector<>();
        for (int month = 1; month <= 12; month++) {
            months.add(String.format("%02d", month));
        }
        monthCombo = new JComboBox<>(months);
        monthCombo.setPreferredSize(new Dimension(60, 25));
        
        // 일 ComboBox 설정 - 초기값은 31일까지
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
        
        // 날짜 입력 패널에 컴포넌트 추가
        p_date_panel.add(yearCombo);
        p_date_panel.add(yearLabel);
        p_date_panel.add(monthCombo);
        p_date_panel.add(monthLabel);
        p_date_panel.add(dayCombo);
        p_date_panel.add(dayLabel);
        
        // 조회 버튼
        JButton check = new JButton("조회");
        p_button_panel.add(check);
        
        // 버튼 스타일 추가
        check.setFont(new Font("SansSerif", Font.BOLD, 12));
        check.setBackground(new Color(30, 135, 61));
        check.setForeground(Color.WHITE);
        check.setFocusPainted(false);
        check.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        check.setOpaque(true);
        check.setContentAreaFilled(true);
        check.setBorderPainted(false);
        
        // 총 가격 레이블
        jlb = new JLabel("총 가격 : ");
        p_total_panel.add(jlb);
        
        // 중앙 상단 패널에 각 패널 추가
        p_center_top.add(p_date_panel);
        p_center_top.add(p_button_panel);
        p_center_top.add(p_total_panel);
        
        // 각 패널에 여백 추가
        p_date_panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        p_button_panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        p_total_panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
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
        
        // 조회 버튼 액션 리스너
        check.addActionListener(e -> {
            try {
                // ComboBox에서 선택된 연/월/일 값 가져오기
                String year = (String) yearCombo.getSelectedItem();
                String month = (String) monthCombo.getSelectedItem();
                String day = (String) dayCombo.getSelectedItem();
                
                System.out.println("일별 검색: " + year + "-" + month + "-" + day);
                
                // 테이블의 모든 데이터 삭제
                clearTable();
                
                // 새 데이터 가져오기 - 일별 데이터 필터링
                list = dao.selectDailyOrders(year, month, day);
                
                // 데이터가 있는 경우에만 테이블에 추가
                if (list != null && !list.isEmpty()) {
                    addDataToTable(list);
                    System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
                } else {
                    System.out.println("해당 일에 데이터가 없습니다: " + year + "년 " + month + "월 " + day + "일");
                    jlb.setText("총 가격 : 0원");
                }
            } catch (Exception ex) {
                System.out.println("일별 데이터 조회 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        // 초기 데이터 로드
        try {
            // ComboBox에서 선택된 연/월/일 값 가져오기
            String year = (String) yearCombo.getSelectedItem();
            String month = (String) monthCombo.getSelectedItem();
            String day = (String) dayCombo.getSelectedItem();
            
            System.out.println("일별 검색: " + year + "-" + month + "-" + day);
            
            // 테이블의 모든 데이터 삭제
            clearTable();
            
            // 새 데이터 가져오기 - 일별 데이터 필터링
            list = dao.selectDailyOrders(year, month, day);
            
            // 데이터가 있는 경우에만 테이블에 추가
            if (list != null && !list.isEmpty()) {
                addDataToTable(list);
                System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
            } else {
                System.out.println("해당 일에 데이터가 없습니다: " + year + "년 " + month + "월 " + day + "일");
                jlb.setText("총 가격 : 0원");
            }
        } catch (Exception ex) {
            System.out.println("일별 데이터 조회 중 오류 발생: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        // 프레임 설정
        setTitle("무인편의점 키오스크");
        setSize(375, 660);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // 연/월에 따라 일(day) ComboBox를 업데이트하는 메서드
    private void updateDayCombo(int year, int month) {
        // 기존에 선택된 날짜 저장 (있으면)
        String selectedDay = null;
        if (dayCombo != null && dayCombo.getSelectedItem() != null) {
            selectedDay = (String) dayCombo.getSelectedItem();
        }
        
        // 해당 월의 마지막 날짜 계산
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1); // 월은 0부터 시작하므로 -1
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // 일 ComboBox 생성 또는 업데이트
        Vector<String> days = new Vector<>();
        for (int day = 1; day <= lastDay; day++) {
            days.add(String.format("%02d", day));
        }
        
        if (dayCombo == null) {
            // 처음 생성하는 경우
            dayCombo = new JComboBox<>(days);
            dayCombo.setPreferredSize(new Dimension(60, 25));
        } else {
            // 기존 ComboBox 업데이트
            dayCombo.removeAllItems();
            for (String day : days) {
                dayCombo.addItem(day);
            }
        }
        
        // 기존에 선택한 날짜가 있고, 이 달에 유효하면 다시 선택
        if (selectedDay != null) {
            int day = Integer.parseInt(selectedDay);
            if (day <= lastDay) {
                dayCombo.setSelectedItem(selectedDay);
            } else {
                // 선택한 날짜가 이번 달에 없으면 마지막 날짜 선택
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
    private void addDataToTable(ArrayList<TotalOrdersProductVO> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            System.out.println("표시할 데이터가 없습니다.");
            return;
        }
        int totalPrice = 0;
        for (TotalOrdersProductVO v : dataList) {
            Vector<Object> row = new Vector<Object>();
            row.add(v.getProductId());
            row.add(v.getProductName());
            row.add(v.getOrderQuantity());
            row.add(v.getOrderDate());
            row.add(v.getCostPriceProduct());
            int itemTotalPrice = v.getOrderQuantity() * v.getCostPriceProduct();
            row.add(itemTotalPrice);
            row.add(v.getRemarks());
            totalPrice += itemTotalPrice;
            ((DefaultTableModel)jtb.getModel()).addRow(row);
        }
        
        // 총 가격 레이블 업데이트
        jlb.setText("총 가격 : " + totalPrice + "원");
    }
}