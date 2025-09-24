# Pet Support Service - Class Diagram

```mermaid
classDiagram
    class SupportRequestServlet {
        -SupportRequestDAO supportRequestDAO
        -Gson gson
        +init() void
        +doGet(HttpServletRequest, HttpServletResponse) void
        +doPost(HttpServletRequest, HttpServletResponse) void
    }

    class NotificationServlet {
        -NotificationDAO notificationDAO
        -Gson gson
        +init() void
        +doGet(HttpServletRequest, HttpServletResponse) void
        +doPost(HttpServletRequest, HttpServletResponse) void
    }

    class SupportRequestDAO {
        +save(SupportRequest) SupportRequest
        +findAll() List~SupportRequest~
        +findById(Long) SupportRequest
        +updateStatus(Long, String) boolean
        +delete(Long) boolean
    }

    class NotificationDAO {
        +save(Notification) Notification
        +findAll() List~Notification~
        +findByType(String) List~Notification~
    }

    class SupportRequest {
        -Long id
        -String name
        -String email
        -String message
        -String status
        -LocalDateTime createdAt
        +getId() Long
        +setId(Long) void
        +getName() String
        +setName(String) void
        +getEmail() String
        +setEmail(String) void
        +getMessage() String
        +setMessage(String) void
        +getStatus() String
        +setStatus(String) void
        +getCreatedAt() LocalDateTime
        +setCreatedAt(LocalDateTime) void
    }

    class Notification {
        -Long id
        -String title
        -String message
        -String type
        -LocalDateTime createdAt
        +getId() Long
        +setId(Long) void
        +getTitle() String
        +setTitle(String) void
        +getMessage() String
        +setMessage(String) void
        +getType() String
        +setType(String) void
        +getCreatedAt() LocalDateTime
        +setCreatedAt(LocalDateTime) void
    }

    class DBConnection {
        -String URL
        -String USER
        -String PASSWORD
        -String DRIVER
        +getConnection() Connection
    }

    %% Relationships
    SupportRequestServlet --> SupportRequestDAO : uses
    NotificationServlet --> NotificationDAO : uses
    SupportRequestDAO --> SupportRequest : manages
    NotificationDAO --> Notification : manages
    SupportRequestDAO --> DBConnection : uses
    NotificationDAO --> DBConnection : uses
```

## Mô tả các class:

### Model Layer
- **SupportRequest**: Model chứa thông tin yêu cầu hỗ trợ
- **Notification**: Model chứa thông tin thông báo

### DAO Layer (Data Access Object)
- **SupportRequestDAO**: Xử lý các thao tác CRUD với bảng support_requests
- **NotificationDAO**: Xử lý các thao tác CRUD với bảng notifications

### Servlet Layer (Controller)
- **SupportRequestServlet**: API endpoint xử lý HTTP requests cho support requests
- **NotificationServlet**: API endpoint xử lý HTTP requests cho notifications

### Utility Layer
- **DBConnection**: Quản lý kết nối cơ sở dữ liệu SQL Server

## Kiến trúc hệ thống:
Hệ thống sử dụng mô hình MVC (Model-View-Controller):
- **Model**: SupportRequest, Notification
- **View**: HTML/JSP files
- **Controller**: Servlet classes
- **DAO**: Lớp truy cập dữ liệu
- **Utility**: Các lớp hỗ trợ như DBConnection