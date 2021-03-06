package demo.spring.angular.auth.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import demo.spring.angular.auth.utils.CommonUtils;
import demo.spring.angular.auth.utils.SystemConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken extends BaseEntity {
	
	@Column(name = "token", nullable = false)
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	public VerificationToken(User user, String token) {
		this.token = token;
		this.user = user;
		this.expiry = CommonUtils.calculateExpiryDate(SystemConstant.EXPIRY_DATE);
	}

	public boolean isExpired() {
		if (null == this.expiry) {
			return false;
		}

		return this.expiry.getTime() < System.currentTimeMillis();
	}
}
