package tn.vhs.dto;

import jakarta.validation.constraints.NotNull;

public class RentalCreateDto {

	@NotNull(message = "{vhsId.notempty}")
	private Long vhsId;

	@NotNull(message = "{userId.notempty}")
	private Long userId;

	public RentalCreateDto(Long vhsId, Long userId) {
		this.vhsId = vhsId;
		this.userId = userId;
	}

	public Long getVhsId() {
		return vhsId;
	}

	public Long getUserId() {
		return userId;
	}

}
