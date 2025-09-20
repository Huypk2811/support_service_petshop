<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Pet Shop - Trang chủ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-4">
    <h1 class="text-center text-primary mb-4">🐶🐱 CHÀO MỪNG ĐẾN PET SHOP 🐱🐶</h1>

    <!-- Thông báo khuyến mãi -->
    <div class="card mb-4 shadow">
        <div class="card-header bg-danger text-white">
            <h4>🔥 Khuyến mãi đặc biệt</h4>
        </div>
        <div class="card-body">
            <p>Mua thức ăn cho chó mèo giảm 20% trong tuần này!</p>
        </div>
    </div>

    <!-- Form hỗ trợ -->
    <div class="card shadow">
        <div class="card-header bg-info text-white">
            <h4>✉️ Gửi yêu cầu hỗ trợ</h4>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/api/support" method="post">
                <div class="mb-3">
                    <label class="form-label">Tên</label>
                    <input type="text" class="form-control" name="name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Nội dung</label>
                    <textarea class="form-control" name="message" rows="3" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Gửi yêu cầu</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
