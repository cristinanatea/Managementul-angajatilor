package com.natea.pm.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.natea.pm.dto.base.ContractInfo;

@Entity
@Table(name = "Contracts")
public class Contract implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int contractId;
	private String contractName;
	private int dailyNorm;
	private int yearlySickDays;
	private int yearlyPaidDays;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contract")
	private Set<User> users = new HashSet<User>();

	public Contract() {
	}

	public Contract(String contractName, int dailyNorm, int yearlySickDays, int yearlyPaidDays) {
		this.contractName = contractName;
		this.dailyNorm = dailyNorm;
		this.yearlySickDays = yearlySickDays;
		this.yearlyPaidDays = yearlyPaidDays;
	}

	public Contract(ContractInfo contractInfo) {
		this.contractName = contractInfo.getContractName();
		this.dailyNorm = contractInfo.getDailyNorm();
		this.yearlySickDays = contractInfo.getYearlySickDays();
		this.yearlyPaidDays = contractInfo.getYearlyPaidDays();
		// TODO Auto-generated constructor stub
	}

	public Set<User> getUsers() {
		return users;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public int getDailyNorm() {
		return dailyNorm;
	}

	public void setDailyNorm(int dailyNorm) {
		this.dailyNorm = dailyNorm;
	}

	public int getYearlySickDays() {
		return yearlySickDays;
	}

	public void setYearlySickDays(int yearlySickDays) {
		this.yearlySickDays = yearlySickDays;
	}

	public int getYearlyPaidDays() {
		return yearlyPaidDays;
	}

	public void setYearlyPaidDays(int yearlyPaidDays) {
		this.yearlyPaidDays = yearlyPaidDays;
	}

	public ContractInfo toDTO() {
		ContractInfo info = new ContractInfo();

		info.setContractId(contractId);
		info.setContractName(contractName);
		info.setDailyNorm(dailyNorm);
		info.setYearlyPaidDays(yearlyPaidDays);
		info.setYearlySickDays(yearlySickDays);

		return info;
	}
}