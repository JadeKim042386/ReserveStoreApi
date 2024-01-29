package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.dto.PartnerSignUpRequest;
import org.zerobase.reservestoreapi.dto.SignUpRequest;

public interface SignUpService {
    /**
     * Member sign up
     */
    void signUp(SignUpRequest signUpRequest);

    /**
     * Store sign up
     */
    void partnerSignUp(PartnerSignUpRequest signUpRequest);
}
