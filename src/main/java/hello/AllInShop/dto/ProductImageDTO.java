package hello.AllInShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {

    //업로드된 파일의 원래 이름
    private String imgName;

    //파일의 UUID 값
    private String uuid;

    //업로드된 파일의 저장 경로
    private String path;

    public String getImageURL() {
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(path+"/s_"+uuid+"_"+imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
