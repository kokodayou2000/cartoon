package org.example.response;

import lombok.Data;
import org.example.model.CartoonDO;
import org.example.model.ChapterDO;

import java.util.List;

@Data
public class CartoonInfo {

    private CartoonDO cartoonInfo;

    private List<ChapterDO> chapterList;
}
