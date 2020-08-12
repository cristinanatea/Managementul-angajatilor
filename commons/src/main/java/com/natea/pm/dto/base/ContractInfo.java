package com.natea.pm.dto.base;

public class ContractInfo {
	private Integer contractId;
	private String contractName;
	private Integer dailyNorm;
	private Integer yearlySickDays;
	private Integer yearlyPaidDays;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Integer getDailyNorm() {
		return dailyNorm;
	}

	public void setDailyNorm(Integer dailyNorm) {
		this.dailyNorm = dailyNorm;
	}

	public Integer getYearlySickDays() {
		return yearlySickDays;
	}

	public void setYearlySickDays(Integer yearlySickDays) {
		this.yearlySickDays = yearlySickDays;
	}

	public Integer getYearlyPaidDays() {
		return yearlyPaidDays;
	}

	public void setYearlyPaidDays(Integer yearlyPaidDays) {
		this.yearlyPaidDays = yearlyPaidDays;
	}

	@Override
	public String toString() {
		return "ContractInfo [contractId=" + contractId + ", contractName=" + contractName + ", dailyNorm=" + dailyNorm
				+ ", yearlySickDays=" + yearlySickDays + ", yearlyPaidDays=" + yearlyPaidDays + "]";
	}
}