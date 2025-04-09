## **OSTK 편의점**
> KOSTA FULL-STACK 개발 웹 애플리케이션 실무 과정 - 1차 프로젝트  
> 프로젝트 GitHub 링크: https://github.com/leo09222022/OneShotTwoKill


---
## 팀원 소개 - 일석이조
공통 역할 : 기획/ 테스터

| 이름   | 역할             | GitHub 링크                           |
|--------|------------------|----------------------------------------|
| 임연수 | 팀장, 백엔드 개발 | [leo09222022](https://github.com/leo09222022) |
| 홍지완 | 백엔드 개발       | [jiewan02](https://github.com/jiewan02) |
| 남세나 | 풀스택 개발       | [SenaNam](https://github.com/SenaNam) |
| 전희재 | DB 설계, 공통 개발 | [jeonhj1015](https://github.com/jeonhj1015) |
| 왕시은 | 풀스택 개발       | [alo-wang](https://github.com/alo-wang) |

---
## :calendar: 프로젝트 기간
- **2025.03.20 ~ 2025.04.09** (총 3주)

---
## 🧾 프로젝트 개요
무인 편의점을 운영하면서 필요한 필수 기능들을 [Admin]  시스템으로 통합 관리하여 제공하고,
편의점을 이용하는 고객들이 보다 간편하게 사용할 수 있도록 간편한 Front 시스템을 구축하여
보다 편리한 B2C(Business to Consumer) 시스템과 B2B(Business to Business) 시스템을 제공합니다.

---
## 🔧 사용 기술 스택
### Backend
- Java 21 (Module system 기반 설정)
- Oracle DB 연동 (ojdbc8 라이브러리 사용)

### Frontend
- Java Swing (데스크톱 UI 개발)

### 협업 & 배포
- Git & GitHub (형상 관리)
- Notion, Figma (기획 및 UI설계)
- Google docs, ERDcloud (문서 및 DB 설계)

---
## 🖥️ 주요 기능
- 🧍 사용자 기능 (User)
  • 상품 검색  
  • 카드 결제  
  • 영수증 발급  
- 🧑‍💼 관리자 기능 (Admin)
  • 로그인  
  • 상품 관리(등록, 수정, 발주, 내역 조회)  
  • 매출 관리
  
---
## 🖼️ 서비스 화면
> **사용자 화면**  
: 고객이 상품을 검색하고 결제하는 무인 키오스크 인터페이스
> 
![image](https://github.com/user-attachments/assets/b0442b13-6b26-418c-b6ef-7c1c12d1f9e0)  


>
>
> **관리자 화면**  
: 상품 등록, 발주 관리, 매출 확인 등을 할 수 있는 통합 관리자 시스템
>
![image](https://github.com/user-attachments/assets/3afbd647-cb5f-479d-9fcf-16cf36162fa6)

![image](https://github.com/user-attachments/assets/c7266576-86b7-4cee-8bfb-2e0de3ac2c94)  

![image](https://github.com/user-attachments/assets/d87641ff-7bd6-479a-88c3-61e551ea24b0)



---
## 📁 프로젝트 폴더 구조: miniproject
```bash
miniproject/
├── lib                       ← 외부 라이브러리 (ojdbc8.jar)
│      
└─ src		              ← 소스 코드 루트
    │  
    ├─category                ← 카테고리 관리
    │  ├─database
    │  └─gui
    │          
    ├─common                  ← 공통 컴포넌트 (예: 커스텀 UI 등)
    │  └─gui
    │          
    ├─db                      ← DB 초기화 및 연결
    │      
    ├─kioskMain               ← 메인 진입점 클래스
    │        
    ├─login                   ← 로그인 관련 GUI
    │  └─gui
    │          
    ├─main                    ← 메인 GUI 화면 (관리자/사용자 등)
    │  └─gui
    │          
    ├─orders                  ← 발주 관련
    │  ├─database
    │  └─gui
    │          
    ├─payment                 ← 결제 관련
    │  ├─database
    │  └─gui
    │          
    ├─product                 ← 상품 관리
    │  ├─database
    │  └─gui
    │          
    ├─sales                   ← 판매 관련
    │  ├─database
    │  └─gui
    │          
    ├─totalordersproduct      ← 전체 발주 상품 통계
    │  ├─database
    │  └─gui
    │          
    └─totalsalesproduct       ← 전체 판매 상품 통계
        ├─database
        └─gui   
``` 
---

## 🗂 DB 설계

📌 [ERDcloud로 작성]  

![image](https://github.com/user-attachments/assets/5aa03dae-c592-476e-9486-a5c3f8a75d34)


---

## 🧠 회고 및 느낀점

이번 프로젝트를 통해 **기획과 설계의 중요성**을 깊이 체감하며 많은 성장을 경험했습니다.  
기능 구현에 앞서 **화면 구조와 흐름을 명확히 설계**하는 것이 이후의 개발 효율성과 완성도에 큰 영향을 준다는 점을 배웠습니다.

### 🛠 기능 우선순위의 명확화
- 필수 기능과 추가 기능을 구분하지 않아 개발 중 혼선이 발생했습니다.  
- 초기 화면 구조를 구체화하지 않고 개발을 시작한 점이 아쉬웠으며, 이후 수정 과정에서 더 많은 시간과 리소스를 소모했습니다.  
- 단순해 보였던 화면도 다양한 기능이 요구된다는 점을 직접 체험했습니다.

### 🗺 체계적인 기획의 필요성
- 다음 프로젝트에서는 **기획 단계에서의 화면 구성과 기능 흐름 설계**의 중요성을 인식하고, 우선순위 설정을 바탕으로 개발을 진행하고자 합니다.  
- 이는 **기능 확장성과 유지보수성 향상**에 큰 도움이 된다는 공감대를 형성했습니다.

---

### 👥 팀원 회고

- **임연수 (팀장, 백엔드 개발)**  
  > 배웠던 기초를 바탕으로 아이디어를 실제로 구현하며 많이 배웠고, 좋은 팀원들과 함께할 수 있어 감사한 경험이었습니다.

- **홍지완 (백엔드 개발)**  
  > 팀원들과의 소통과 협업을 통해 많은 것을 배울 수 있었고, 서로를 배려하는 분위기가 인상 깊었습니다.

- **남세나 (풀스택 개발)**  
  > 팀원들이 각자의 역할에 최선을 다해준 덕분에 전반적인 과정을 경험할 수 있었고, 뜻깊은 시간이었습니다.

- **전희재 (DB 설계, 공통 개발)**  
  > 수업에서 배운 내용을 활용해보며 부족한 점은 팀원들의 도움으로 채워나갈 수 있어 감사했습니다.

- **왕시은 (풀스택 개발)**  
  > 내가 어떤 점이 부족하고 어떤 걸 잘할 수 있는지를 돌아보는 계기가 되었고, 협업의 소중함을 더 깊이 깨달았습니다.

---

