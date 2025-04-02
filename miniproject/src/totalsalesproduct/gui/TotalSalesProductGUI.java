package totalsalesproduct.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.gui.AdminMainGUI;
import totalsalesproduct.database.TotalSalesProductDAO;
import totalsalesproduct.database.TotalSalesProductVO;

public class TotalSalesProductGUI extends JFrame {
    JTextField jtf;
    JLabel jlabel;
    JTextField yearField, monthField, dayField; // 연/월/일 입력 필드 추가
    Vector<String> colNames = new Vector<String>();
    Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
    private JScrollPane jsp; // 테이블을 위한 스크롤 팬
    private JTable jtb; // 데이터 표시를 위한 테이블
    String[] sArr = {"바코드", "상품명", "판매량", "원가", "판매가", "총수익", "순이익", "이익률"}; // 행이름
    ArrayList<TotalSalesProductVO> list = new ArrayList<TotalSalesProductVO>(); // 열 데이터
    TotalSalesProductVO vo = new TotalSalesProductVO();
    TotalSalesProductDAO dao = new TotalSalesProductDAO();
    
    public TotalSalesProductGUI() {
        setTitle("매출관리");
        JPanel p_top = new JPanel();        // 상단 패널 추가
        JPanel p_north = new JPanel();      // 기존 북쪽 패널 (날짜 입력 및 버튼)
        JPanel p_center = new JPanel();     // 컨텐츠 영역
        JPanel p_south = new JPanel();      // 하단
        
        // 상단, 하단 패널 배경색 설정
        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);	
        
        p_north.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // 간격 조정
        // 중요: p_center를 BorderLayout으로 변경
        p_center.setLayout(new BorderLayout());
        p_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        // 상단 패널 설정: 뒤로가기 버튼
        JButton btnBack = new JButton("< 뒤로가기");
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_top.add(btnBack);
        btnBack.setBorderPainted(true);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFocusPainted(false);
        
        // 버튼에 이벤트 추가
        btnBack.addActionListener(e -> {
            this.setVisible(false);
            new AdminMainGUI();
        });
        
        // 하단 패널 설정: 관리자 화면 종료 라벨
        JLabel lblExit = new JLabel("관리자 화면 종료");
        p_south.add(lblExit);
        
        // 라벨에 마우스 이벤트 추가
        lblExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new main.gui.MainGUI(); // 메인 화면으로 돌아가기
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                lblExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
        });
        
        // 연/월/일 라벨과 입력 필드 추가
        JLabel yearLabel = new JLabel("년");
        JLabel monthLabel = new JLabel("월");
        JLabel dayLabel = new JLabel("일");
        
        yearField = new JTextField(4); // 연도 입력 필드 (4자리)
        monthField = new JTextField(2); // 월 입력 필드 (2자리)
        dayField = new JTextField(2); // 일 입력 필드 (2자리)
        
        // 기본값 설정 - 현재 연도와 월
        java.util.Calendar cal = java.util.Calendar.getInstance();
        yearField.setText(String.valueOf(cal.get(java.util.Calendar.YEAR)));
        // 월은 0부터 시작하므로 +1 필요, 그리고 한 자리 수일 경우 앞에 0 추가
        monthField.setText(String.format("%02d", cal.get(java.util.Calendar.MONTH) + 1));
        dayField.setText(String.format("%02d", cal.get(java.util.Calendar.DAY_OF_MONTH)));
        
        // 버튼 생성
        JButton tot_btn = new JButton("전체");
        JButton day_btn = new JButton("일별");
        JButton month_btn = new JButton("월별");
        
        // 북쪽 패널에 컴포넌트 추가 (왼쪽부터 순서대로)
        p_north.add(yearField);
        p_north.add(yearLabel);
        p_north.add(monthField);
        p_north.add(monthLabel);
        p_north.add(dayField);
        p_north.add(dayLabel);
        
        // 버튼들과의 간격을 약간 주기 위해 빈 라벨 추가 (선택 사항)
        p_north.add(new JLabel("   "));
        
        p_north.add(tot_btn);
        p_north.add(month_btn);
        p_north.add(day_btn);
        
        // 컬럼명 추가
        for(String s : sArr) {
            colNames.add(s);
        }
        
        // 테이블 모델 생성 - Vector 사용
        DefaultTableModel model = new DefaultTableModel(rowData, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가능하게 설정
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // 각 열의 데이터 타입 지정
                if (columnIndex >= 2 && columnIndex <= 6) {
                    return Integer.class; // 판매수량, 원가, 판매가, 총 판매수익, 순이익
                } else if (columnIndex == 7) {
                    return Double.class; // 이익률
                }
                return String.class; // 나머지는 문자열
            }
        };
        
        // 테이블 생성 및 설정
        jtb = new JTable(model);
        jtb.getTableHeader().setReorderingAllowed(false); // 열 이동 불가
        jtb.setPreferredScrollableViewportSize(new Dimension(750, 500)); // 테이블 사이즈 설정
        
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
        jsp.setPreferredSize(new Dimension(750, 500)); // 스크롤 패널 크기 설정
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // 중앙 패널에 스크롤 패널 추가 (중요: 이제 BorderLayout이므로 정상 작동)
        p_center.add(jsp, BorderLayout.CENTER);
        
        // 프레임에 패널 추가
        add(p_top, BorderLayout.NORTH);      // 최상단에 뒤로가기 버튼 패널 추가
        add(p_north, BorderLayout.NORTH);    // p_top 아래에 날짜 입력 및 버튼 패널 추가
        add(p_center, BorderLayout.CENTER);  // 중앙에 테이블 패널 추가
        add(p_south, BorderLayout.SOUTH);    // 하단에 종료 라벨 패널 추가
        
        tot_btn.addActionListener(e -> {
            try {
                // 기존 데이터 삭제
                rowData.clear();
                ((DefaultTableModel)jtb.getModel()).setRowCount(0);
                
                // 새 데이터 가져오기
                list = dao.selectAllSalesProduct();
                
                // 데이터 추가
                addDataToTable(list);
                
                System.out.println("데이터 로드 완료: " + list.size() + "개의 행");
            } catch (Exception ex) {
                System.out.println("전체 데이터 로드 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        month_btn.addActionListener(e -> {
            try {
                // 입력된 연/월 값 가져오기
                String year = yearField.getText().trim();
                String month = monthField.getText().trim();
                
                // 한 자리 월은 앞에 0 추가
                if (month.length() == 1) {
                    month = "0" + month;
                }
                
                System.out.println("월별 검색: " + year + "-" + month);
                
                // 기존 데이터 삭제
                rowData.clear();
                ((DefaultTableModel)jtb.getModel()).setRowCount(0);
                
                // 새 데이터 가져오기 - 월별 데이터 필터링
                list = dao.selectMonthlySalesProduct(year, month);
                
                // 데이터 추가
                addDataToTable(list);
                
                System.out.println("월별 데이터 로드 완료: " + year + "년 " + month + "월, " + list.size() + "개의 행");
            } catch (Exception ex) {
                System.out.println("월별 데이터 조회 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        day_btn.addActionListener(e -> {
            try {
                // 입력된 연/월/일 값 가져오기
                String year = yearField.getText().trim();
                String month = monthField.getText().trim();
                String day = dayField.getText().trim();
                
                // 한 자리 월/일은 앞에 0 추가
                if (month.length() == 1) {
                    month = "0" + month;
                }
                if (day.length() == 1) {
                    day = "0" + day;
                }
                
                System.out.println("일별 검색: " + year + "-" + month + "-" + day);
                
                // 기존 데이터 삭제
                rowData.clear();
                ((DefaultTableModel)jtb.getModel()).setRowCount(0);
                
                // 새 데이터 가져오기 - 일별 데이터 필터링
                list = dao.selectDailySalesProduct(year, month, day);
                
                // 데이터 추가
                addDataToTable(list);
                
                System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
            } catch (Exception ex) {
                System.out.println("일별 데이터 조회 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        // 초기 데이터 로드
        try {
            list = dao.selectAllSalesProduct();
            addDataToTable(list);
        } catch (Exception ex) {
            System.out.println("초기 데이터 로드 중 오류 발생: " + ex.getMessage());
        }
        
        // 프레임 설정
        setSize(800, 600);
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 메인 프로그램은 계속 실행
    }
    
    // 테이블에 데이터 추가하는 메서드 (중복 코드 제거)
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
}