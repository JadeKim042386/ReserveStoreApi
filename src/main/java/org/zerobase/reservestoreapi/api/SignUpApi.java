package org.zerobase.reservestoreapi.api;

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
     * request sign up<br>
     * request partner sign up example:
     * <pre class="code">
     * /api/v1/signup?partner=true
     *
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
     * @param isPartnerSignUp determine whether or not to sign up a partner
     */
    @BindingResultHandler(message = "validation error during sign up")
    @PostMapping
    public ResponseEntity<ApiResponse> requestSignUp(
            @RequestParam("partner") Boolean isPartnerSignUp,
            @RequestBody @Validated SignUpRequest signUpRequest,
            BindingResult bindingResult) {

        if (isPartnerSignUp) {
            signUpService.partnerSignUp(signUpRequest);
        } else {
            signUpService.signUp(signUpRequest);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of("You have successfully signed up"));
    }

    /** Duplicated Check <br> Verify that one of username, nickname, storeName already exists.*/
    @GetMapping("/exists")
    public ResponseEntity<ApiResponse> checkExists(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String storeName) {
        if ((StringUtils.hasText(username) || StringUtils.hasText(nickname)) && memberService.isExistsUsernameOrNickname(username, nickname)) {
            throw new SignUpException(ErrorCode.ALREADY_EXISTS_USERNAME_OR_NICKNAME);
        } else if (StringUtils.hasText(storeName) && storeService.isExistsStoreName(storeName)) {
            throw new StoreException(ErrorCode.ALREADY_EXISTS_STORE_NAME);
        }
        return ResponseEntity.ok(ApiResponse.of("you can use"));
    }
}
