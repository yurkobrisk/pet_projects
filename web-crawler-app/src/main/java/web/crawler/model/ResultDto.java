package web.crawler.model;

import java.util.ArrayList;
import java.util.List;

public class ResultDto {

    private List<ResultPageDto> resultPageDtoList
            = new ArrayList<>();

    public List<ResultPageDto> getResultPageDtoList() {
        return resultPageDtoList;
    }

    public void setResultPageDtoList(List<ResultPageDto> resultPageDtoList) {
        this.resultPageDtoList = resultPageDtoList;
    }
}
