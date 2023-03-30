package hello.AllInShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {
    private Long id;

    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    @Email(message = "이메일 형식으로 입력해야합니다")
    private String email;

    @NotBlank(message = "닉네임은 반드시 입력해야합니다.")
    private String nickname;

    @Min(value = 4, message = "최소 4글자 이상으로 작성해야합니다.")
    private String password;

    private boolean fromSocial;

    @NotBlank(message = "주소는 반드시 입력해야합니다.")
    private String city;
    @NotBlank(message = "주소는 반드시 입력해야합니다.")
    private String street;
    @Positive(message = "우편번호는 반드시 입력해야합니다.")
    private int zipcode;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
