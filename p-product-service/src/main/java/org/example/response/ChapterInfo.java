package org.example.response;

import lombok.Data;
import org.example.model.PaperDO;

import java.util.List;

@Data
public class ChapterInfo {

    public List<PaperDO> paperList;

}
