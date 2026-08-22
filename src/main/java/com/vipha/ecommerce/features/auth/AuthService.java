package com.vipha.ecommerce.features.auth;

import com.vipha.ecommerce.features.auth.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request );
}
