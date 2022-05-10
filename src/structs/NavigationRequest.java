package structs;

import java.util.List;

public record NavigationRequest(NavigationType navigationType, Object[] args) {}
