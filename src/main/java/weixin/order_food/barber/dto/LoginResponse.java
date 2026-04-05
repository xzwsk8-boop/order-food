package weixin.order_food.barber.dto;

public class LoginResponse {
    private String token;
    private Long userId;
    private Boolean isBarber;
    private Long barberId;

    public LoginResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
        this.isBarber = false;
    }

    public LoginResponse(String token, Long userId, Boolean isBarber, Long barberId) {
        this.token = token;
        this.userId = userId;
        this.isBarber = isBarber;
        this.barberId = barberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsBarber() {
        return isBarber;
    }

    public void setIsBarber(Boolean isBarber) {
        this.isBarber = isBarber;
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }
}
