package totalordersproduct.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.gui.ProductManagementGUI;
import totalordersproduct.database.TotalOrdersProductDAO;
import totalordersproduct.database.TotalOrdersProductVO;

public class TotalOrdersProductGUI extends JFrame {
    JTextField jtf;
    JLabel jlabel;
    JTextField yearField, monthField, dayField; // 연/월/일 입력 필드 추가
    Vector<String> colNames = new Vector<String>();
    Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
    private JScrollPane jsp; // 테이블을 위한 스크롤 팬
    private JTable jtb; // 데이터 표시를 위한 테이블
    String[] sArr = {"바코드", "상품명", "발주수량", "발주일", "원가", "총가격", "비고사항"}; // 행이름
    ArrayList<TotalOrdersProductVO> list = new ArrayList<TotalOrdersProductVO>(); // 열 데이터
    TotalOrdersProductVO vo = new TotalOrdersProductVO();
    TotalOrdersProductDAO dao = new TotalOrdersProductDAO();
    
    public TotalOrdersProductGUI() {
        setTitle("발주 내역");
        
        // 전체 레이아웃 설정
        setLayout(new BorderLayout());
        
        // 패널 생성
        JPanel p_top = new JPanel();        // 상단 패널 (뒤로가기 버튼)
        JPanel p_center = new JPanel();     // 중앙 패널 (컨텐츠 영역)
        JPanel p_center_top = new JPanel(); // 중앙 상단 패널 (날짜 입력 및 버튼)
        JPanel p_date_panel = new JPanel(); // 날짜 입력 패널
        JPanel p_button_panel = new JPanel(); // 버튼 패널
        JPanel p_center_mid = new JPanel(); // 중앙 중간 패널 (테이블)
        JPanel p_south = new JPanel();      // 하단 패널 (관리자 화면 종료)
        
        // 패널 배경색 설정
        p_top.setBackground(Color.WHITE);
        p_south.setBackground(Color.WHITE);
        
        // 패널 레이아웃 설정
        p_top.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_center.setLayout(new BorderLayout());
        p_center_top.setLayout(new BoxLayout(p_center_top, BoxLayout.Y_AXIS)); // 수직 배치로 변경
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
            dispose(); // 현재 창 닫기
            new ProductManagementGUI(); // 관리자 메인 화면으로 돌아가기
        });
        
        // 하단 패널: 관리자 화면 종료 레이블
        JLabel lblExit = new JLabel("관리자 화면 종료");
        p_south.add(lblExit);
        
        // 레이블에 마우스 이벤트 추가
        lblExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // 현재 창 닫기
                new main.gui.MainGUI(); // 메인 화면으로 돌아가기
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                lblExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
        });
        
        // 날짜 입력 패널: 연/월/일 입력 필드와 레이블
        JLabel yearLabel = new JLabel("년");
        JLabel monthLabel = new JLabel("월");
        JLabel dayLabel = new JLabel("일");
        
        yearField = new JTextField(4); // 연도 입력 필드 (4자리)
        monthField = new JTextField(2); // 월 입력 필드 (2자리)
        dayField = new JTextField(2); // 일 입력 필드 (2자리)
        
        // 기본값 설정 - 현재 연도와 월, 일
        java.util.Calendar cal = java.util.Calendar.getInstance();
        yearField.setText(String.valueOf(cal.get(java.util.Calendar.YEAR)));
        monthField.setText(String.format("%02d", cal.get(java.util.Calendar.MONTH) + 1));
        dayField.setText(String.format("%02d", cal.get(java.util.Calendar.DAY_OF_MONTH)));
        
        // 날짜 입력 패널에 컴포넌트 추가
        p_date_panel.add(yearField);
        p_date_panel.add(yearLabel);
        p_date_panel.add(monthField);
        p_date_panel.add(monthLabel);
        p_date_panel.add(dayField);
        p_date_panel.add(dayLabel);
        
        JButton check = new JButton("조회");
        
        // 버튼 패널에 버튼 추가
        p_button_panel.add(check);
        
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
        
        // 테이블 크기 설정 - 이 부분이 중요
        // 테이블 자체의 크기를 고정값으로 설정하지 않고, 스크롤 팬의 크기에 맞게 자동 조정되도록 함
        jtb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 자동 리사이즈 비활성화
        
        // 각 열의 너비 설정
        jtb.getColumnModel().getColumn(0).setPreferredWidth(70);  // 바코드
        jtb.getColumnModel().getColumn(1).setPreferredWidth(150); // 상품명
        jtb.getColumnModel().getColumn(2).setPreferredWidth(70);  // 판매량
        jtb.getColumnModel().getColumn(3).setPreferredWidth(70);  // 원가
        jtb.getColumnModel().getColumn(4).setPreferredWidth(70);  // 판매가
        jtb.getColumnModel().getColumn(5).setPreferredWidth(100); // 총수익
        jtb.getColumnModel().getColumn(6).setPreferredWidth(100); // 순이익
        
        
        // 숫자 열에 오른쪽 정렬 렌더러 적용
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        for (int i = 2; i <= 6; i++) {
            jtb.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
        
        // 스크롤 패널에 테이블 추가
        jsp = new JScrollPane(jtb);
        
        // 스크롤 패널의 크기를 창 크기보다 작게 설정
        jsp.setPreferredSize(new Dimension(350, 380)); // 스크롤 패널 크기 조정 (작은 화면에 맞게)
        
        // 스크롤바 항상 표시
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
        
        
        
        check.addActionListener(e -> {
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
                
                
                // 먼저 테이블의 모든 데이터 삭제
                clearTable();
                
                
                // 새 데이터 가져오기 - 일별 데이터 필터링
                list = dao.selectDailyOrders(year, month, day);
                
                
                // 데이터가 있는 경우에만 테이블에 추가
                if (list != null && !list.isEmpty()) {
                    addDataToTable(list);
                    System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
                } else {
                    System.out.println("해당 일에 데이터가 없습니다: " + year + "년 " + month + "월 " + day + "일");
                }
                
                System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
            } catch (Exception ex) {
                System.out.println("일별 데이터 조회 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        // 초기 데이터 로드
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
            
            
            // 먼저 테이블의 모든 데이터 삭제
            clearTable();
            
            
            // 새 데이터 가져오기 - 일별 데이터 필터링
            list = dao.selectDailyOrders(year, month, day);
            
            
            // 데이터가 있는 경우에만 테이블에 추가
            if (list != null && !list.isEmpty()) {
                addDataToTable(list);
                System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
            } else {
                System.out.println("해당 일에 데이터가 없습니다: " + year + "년 " + month + "월 " + day + "일");
            }
            
            System.out.println("일별 데이터 로드 완료: " + year + "년 " + month + "월 " + day + "일, " + list.size() + "개의 행");
        } catch (Exception ex) {
            System.out.println("일별 데이터 조회 중 오류 발생: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        // 프레임 설정
        setTitle("무인편의점 키오스크");
        setSize(375, 660);  // 다른 화면과 같은 크기로 조정
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setVisible(true);
        setResizable(false); // 리사이즈 제어
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X 버튼 클릭 시 프로그램 종료
    }
    // 테이블의 모든 데이터를 지우는 메서드
    private void clearTable() {
        // 테이블 모델 가져오기
        DefaultTableModel model = (DefaultTableModel) jtb.getModel();
        // 행 수를 0으로 설정하여 모든 행 삭제
        model.setRowCount(0);
        // 데이터 목록도 비우기
        list.clear();
        rowData.clear();
    }
    // 테이블에 데이터 추가하는 메서드
    private void addDataToTable(ArrayList<TotalOrdersProductVO> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            System.out.println("표시할 데이터가 없습니다.");
            return;
        }
        
        for (TotalOrdersProductVO v : dataList) {
            Vector<Object> row = new Vector<Object>();
            row.add(v.getProductId());
            row.add(v.getProductName());
            row.add(v.getOrderQuantity());
            row.add(v.getOrderDate());
            row.add(v.getCostPriceProduct());
            row.add(v.getTotCostPriceProduct());
            row.add(v.getRemarks());
            ((DefaultTableModel)jtb.getModel()).addRow(row);
        }
    }
}