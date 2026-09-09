package com.example.bookapi.common;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {

    @Test
    void okUsesSuccessCodeAndCarriesData() {
        ApiResponse<String> response = ApiResponse.ok("内容");

        assertThat(response.getCode()).isZero();
        assertThat(response.getMsg()).isEqualTo("成功");
        assertThat(response.getData()).isEqualTo("内容");
    }

    @Test
    void okWithoutArgumentLeavesDataNull() {
        ApiResponse<Void> response = ApiResponse.ok();

        assertThat(response.getCode()).isZero();
        assertThat(response.getData()).isNull();
    }

    @Test
    void failUsesCodeAndMessageFromErrorCode() {
        ApiResponse<Void> response = ApiResponse.fail(ErrorCode.BOOK_NOT_FOUND);

        assertThat(response.getCode()).isEqualTo(1002);
        assertThat(response.getMsg()).isEqualTo("图书不存在");
        assertThat(response.getData()).isNull();
    }

    @Test
    void failCanOverrideMessageAndCarryData() {
        Map<String, String> fieldErrors = Collections.singletonMap("title", "书名不能为空");

        ApiResponse<Map<String, String>> response =
                ApiResponse.fail(ErrorCode.PARAM_INVALID, "请求参数不正确", fieldErrors);

        assertThat(response.getCode()).isEqualTo(1001);
        assertThat(response.getMsg()).isEqualTo("请求参数不正确");
        assertThat(response.getData()).containsEntry("title", "书名不能为空");
    }
}
