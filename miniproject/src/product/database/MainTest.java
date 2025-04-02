package product.database;

import java.util.ArrayList;
import java.util.Scanner;

public class MainTest {
    private static Scanner scanner;
    private static ProductDAO productDAO;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        productDAO = new ProductDAO();
        
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // 입력 버퍼 비우기
            
            switch (choice) {
                case 1 -> printProducts();     // 상품 목록 출력
                case 2 -> insertProduct();     // 상품 추가
                case 3 -> updateProduct();     // 상품 수정
                case 4 -> deleteProduct();     // 상품 삭제
                case 5 -> findByName();        // 상품 이름으로 조회
                case 0 -> {
                    System.out.println("프로그램 종료");
                    scanner.close();
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n*** 상품 관리 프로그램 ***");
        System.out.println("1. 전체 상품 조회");
        System.out.println("2. 상품 추가");
        System.out.println("3. 상품 수정");
        System.out.println("4. 상품 삭제");
        System.out.println("5. 상품 검색 (이름)");
        System.out.println("0. 종료");
        System.out.print("메뉴를 선택하세요: ");
    }

    // 1. 모든 상품 조회
    private static void printProducts() {
        ArrayList<ProductVO> list = productDAO.findAll();
        System.out.println("상품ID\t이름\t가격\t재고\t등록일");
        for (ProductVO p : list) {
            System.out.println(p.getProductId() + "\t" + p.getProductName() + "\t" + p.getSalePrice() + "\t" + p.getStock() + "\t" + p.getCreatedAt());
        }
    }

    // 2. 상품 추가
    private static void insertProduct() {
        System.out.print("상품 ID 입력: ");
        String productId = scanner.nextLine();
        System.out.print("카테고리 ID 입력: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        System.out.print("상품 이름 입력: ");
        String productName = scanner.nextLine();
        System.out.print("최적 재고 입력: ");
        int optimalStock = scanner.nextInt();
        System.out.print("현재 재고 입력: ");
        int stock = scanner.nextInt();
        System.out.print("원가 입력: ");
        int costPrice = scanner.nextInt();
        System.out.print("판매가 입력: ");
        int salePrice = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        ProductVO newProduct = new ProductVO(productId, categoryId, productName, optimalStock, stock, costPrice, salePrice, null, null);
        int result = productDAO.insertProduct(newProduct);
        
        if (result == 1) {
            System.out.println("상품 등록 성공!");
        } else {
            System.out.println("상품 등록 실패!");
        }
    }

    // 3. 상품 수정
    private static void updateProduct() {
        System.out.print("수정할 상품 ID 입력: ");
        String productId = scanner.nextLine();
        
        System.out.print("새 카테고리 ID 입력: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        System.out.print("새 상품 이름 입력: ");
        String productName = scanner.nextLine();
        System.out.print("새 최적 재고 입력: ");
        int optimalStock = scanner.nextInt();
        System.out.print("새 현재 재고 입력: ");
        int stock = scanner.nextInt();
        System.out.print("새 원가 입력: ");
        int costPrice = scanner.nextInt();
        System.out.print("새 판매가 입력: ");
        int salePrice = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        
        ProductVO updatedProduct = new ProductVO(productId, categoryId, productName, optimalStock, stock, costPrice, salePrice, null, null);
        int result = productDAO.updateProduct(updatedProduct);
        
        if (result == 1) {
            System.out.println("상품 수정 성공!");
        } else {
            System.out.println("상품 수정 실패!");
        }
    }

    // 4. 상품 삭제
    private static void deleteProduct() {
        System.out.print("삭제할 상품 ID 입력: ");
        String productId = scanner.nextLine();
        
        productDAO.deleteProduct(productId);
        System.out.println("상품 삭제 완료.");
    }

    // 5. 상품 이름으로 검색
    private static void findByName() {
        System.out.print("검색할 상품 이름 입력: ");
        String productName = scanner.nextLine();
        
        ProductVO product = productDAO.findByName(productName);
        if (product != null) {
            System.out.println("상품 ID: " + product.getProductId());
            System.out.println("카테고리 ID: " + product.getCategoryId());
            System.out.println("상품명: " + product.getProductName());
            System.out.println("최적 재고: " + product.getOptimalStock());
            System.out.println("현재 재고: " + product.getStock());
            System.out.println("원가: " + product.getCostPrice());
            System.out.println("판매가: " + product.getSalePrice());
            System.out.println("등록일: " + product.getCreatedAt());
            System.out.println("수정일: " + product.getUpdatedAt());
        } else {
            System.out.println("해당 이름의 상품이 없습니다.");
        }
    }
}

