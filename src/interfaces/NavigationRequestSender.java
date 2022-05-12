package interfaces;

import structs.NavigationRequest;

public interface NavigationRequestSender {
    void triggerNavigationRequest(NavigationRequest request);
}
