package org.zerobase.reservestoreapi.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zerobase.reservestoreapi.aop.BindingResultHandler;
import org.zerobase.reservestoreapi.dto.request.SignUpRequest;
import org.zerobase.reservestoreapi.dto.response.ApiResponse;
import org.zerobase.reservestoreapi.exception.SignUpException;
import org.zerobase.reservestoreapi.exception.StoreException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.service.MemberService;
import org.zerobase.reservestoreapi.service.SignUpService;
import org.zerobase.reservestoreapi.service.StoreService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/signup")
public class SignUpApi {
    private final SignUpService signUpService;
    private final MemberService memberService;
    private final StoreService storeService;

    /**
     * request sign up
     *
     * <pre>
     * Request Example:
     * - /api/v1/signup?partner=true
     * {
     *      "username": "username",
     *      "password": "pw",
     *      "nickname": "nickname",
     *      "memberRole": "STORE",
     *      "zipcode": "11111",
     *      "street": "street",
     *      "detail": "detail",
     *      "phone": "01011111111",
     *      "partnerInfo": {
     *          "storeName": "store",
     *          "startTime": "09:00",
     *          "lastTime": "18:00",
     *          "intervalTime": 30,
     *          "storeType": "BAR"
     *      }
     * }
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *      "message": "You have successfully signed up"
     * }
     * </pre>
     *
     * @throws SignUpException if already exists username or nickname or store name
     */
    @Operation(summary = "Request Sign Up")
    @BindingResultHandler(message = "validation error during sign up")
    @PostMapping
    public ResponseEntity<ApiResponse> requestSignUp(
            @RequestParam("partner") Boolean isPartnerSignUp,
            @RequestBody @Validated SignUpRequest signUpRequest,
            BindingResult bindingResult) {

        if (isPartnerSignUp) {
            checkExists(signUpRequest.getUsername(), signUpRequest.getNickname(), signUpRequest.getPartnerInfo().getStoreName());
            signUpService.partnerSignUp(signUpRequest);
        } else {
            checkExists(signUpRequest.getUsername(), signUpRequest.getNickname(), null);
            signUpService.signUp(signUpRequest);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of("You have successfully signed up"));
    }

    /**
     * Duplicated Check <br>
     * Verify that one of username, nickname, storeName already exists.
     */
    private void checkExists(String username, String nickname, String storeName) {
        if ((StringUtils.hasText(username) || StringUtils.hasText(nickname))
                && memberService.isExistsUsernameOrNickname(username, nickname)) {
            throw new SignUpException(ErrorCode.ALREADY_EXISTS_USERNAME_OR_NICKNAME);
        } else if (StringUtils.hasText(storeName) && storeService.isExistsStoreName(storeName)) {
            throw new StoreException(ErrorCode.ALREADY_EXISTS_STORE_NAME);
        }
    }
}
