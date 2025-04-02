package totalsalesproduct.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import totalsalesproduct.database.TotalSalesProductDAO;
import totalsalesproduct.database.TotalSalesProductVO;

public class TotalSalesProductGUI extends JFrame {
    JTextField jtf;
    JLabel jlabel;
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
        JPanel p_north = new JPanel();
        JPanel p_center = new JPanel();
        JPanel p_south = new JPanel();
        
        p_north.setLayout(new FlowLayout());
        // 중요: p_center를 BorderLayout으로 변경
        p_center.setLayout(new BorderLayout());
        p_south.setLayout(new FlowLayout());
        
        JButton day_btn = new JButton("일별");
        JButton month_btn = new JButton("월별");
        p_north.add(day_btn);
        p_north.add(month_btn);
        
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
        add(p_north, BorderLayout.NORTH);
        add(p_center, BorderLayout.CENTER);
        add(p_south, BorderLayout.SOUTH);
        
        day_btn.addActionListener(e -> {
            // 기존 데이터 삭제
            rowData.clear();
            ((DefaultTableModel)jtb.getModel()).setRowCount(0);
            
            // 새 데이터 가져오기
            list = dao.selectAllSalesProduct();
            for(TotalSalesProductVO vo : list) {
            	System.out.println(vo);
            }
            // 데이터 추가
            for (TotalSalesProductVO v : list) {
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
            
            System.out.println("데이터 로드 완료: " + rowData.size() + "개의 행");
        });
        
        month_btn.addActionListener(e -> {
            // 월별 데이터를 위한 기능 구현
            System.out.println("월별 버튼 클릭됨");
        });
        
        // 프레임 설정
        setSize(800, 600);
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}