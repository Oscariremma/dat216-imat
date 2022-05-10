package interfaces;

public interface HeaderNavigationListener {
    void goToHome();
    void goToFavorites();
    void goToOrderHistory();
    void goToCart();
    void goToSearchResult(String quarry);
    void goBack();
}
