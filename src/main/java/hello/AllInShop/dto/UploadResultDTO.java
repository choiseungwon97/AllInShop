package hello.AllInShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO {

    //업로드된 파일의 원래 이름
    private String fileName;

    //파일의 UUID 값
    private String uuid;

    //업로드된 파일의 저장 경로
    private String folderPath;

    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(folderPath+"/s_"+uuid+"_"+fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
