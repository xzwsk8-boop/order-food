# 接口文档分类指南 (API Documentation)

本项目目前已经融合了多个业务模块。为了更好地进行管理和对接，我们将所有的接口（包括现有的以及即将新增的）分为三大主要模块：

## 模块一：通用与权限管理 (Common & Admin)
此模块负责系统的基础运行、权限校验以及AI交互能力。

*   **管理员权限 API** (`/api/admins`)
    *   `GET /api/admins/check` - 检查是否为管理员
    *   `POST /api/admins/add` - 添加管理员
    *   `GET /api/admins/list` - 获取管理员列表
    *   `DELETE /api/admins/delete/{id}` - 删除管理员
*   **AI 聊天交互 API** (`/api/chat`)
    *   `GET /api/chat` - 发送聊天信息与AI互动

## 模块二：画作与酒水电商 (Art & Drinks E-commerce)
此模块包含最初的酒水点单功能，以及后来增加的画作定制与订单系统。

*   **画作类型 API** (`/api/art-types`)
    *   `GET /api/art-types/getList` - 获取上架的画作列表
    *   `POST /api/art-types/add` - 添加新画作
    *   `DELETE /api/art-types/delete/{id}` - 删除画作
*   **订单管理 API** (`/api/orders`)
    *   `POST /api/orders/create` - 创建画作/电商订单
    *   `GET /api/orders/getList` - 获取订单列表（支持根据 openid 过滤）
    *   `GET /api/orders/{id}` - 获取订单详情
*   **酒水管理 API** (`/api/admin/drinks`)
    *   后台增删改查及分页接口等

---

## 模块三：理发店预约 (Barbershop Appointment) —— [即将新增]
此模块为全新的理发店预约小程序专属模块，预计包含以下子模块结构：

*   **理发师管理 API** (`/api/barbers`)
    *   获取理发师列表、详情（展示特长、评价等）。
*   **服务项目 API** (`/api/services`)
    *   获取门店服务项目列表（如洗剪吹、烫发、染发等）。
*   **预约订单 API** (`/api/appointments`)
    *   用户发起预约（选择理发师、时间段、服务项目）。
    *   查询个人的预约记录。
    *   管理员查看/处理所有的预约记录。

---

### 接下来建议的数据库设计 (理发店预约模块)
为了实现“模块三”，我们需要在数据库中增加相关表。建议如下：

1.  **理发师表 (`barbers`)**: 存储理发师基本信息（姓名、头像、简介、状态等）。
2.  **服务项目表 (`service_items`)**: 存储理发项目（名称、价格、预计耗时等）。
3.  **预约单表 (`appointments`)**: 存储用户的预约信息（用户openid、理发师ID、服务项目ID、预约时间、状态：待确认/已确认/已完成/已取消）。
