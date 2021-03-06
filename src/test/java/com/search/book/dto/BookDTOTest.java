package com.search.book.dto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {BookDTO.Req.class})
public class BookDTOTest {

    @Test
    public void 네이버_URI_생성_테스트() {
        // given
        String query = "테스트";
        String sort = "accuracy";
        int size = 10;
        BookDTO.Req request = BookDTO.Req.builder()
                .query(query)
                .sort(sort)
                .size(size)
                .build();

        // when & then
        String url = request.getNaverUrl("http://test.com");
        assertThat(url)
                .contains("query=" + query)
                .contains("sort=" + "sim")
                .contains("display=" + size);
    }

}
