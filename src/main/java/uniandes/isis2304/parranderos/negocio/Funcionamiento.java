package uniandes.isis2304.parranderos.negocio;

public class Funcionamiento {
	
	private int semana;
	private Long prodmas;
	private Long cpmas;
	private Long prodmenos;
	private Long cpmenos;
	private Long provmas;
	private Long cprovmas;
	private Long provmenos;
	private Long cprovmenos;
	
	public Funcionamiento(int semana, Long prodmas, Long cpmas, Long prodmenos, Long cpmenos, Long provmas,
			Long cprovmas, Long provmenos, Long cprovmenos) {
		
		this.semana = semana;
		this.prodmas = prodmas;
		this.cpmas = cpmas;
		this.prodmenos = prodmenos;
		this.cpmenos = cpmenos;
		this.provmas = provmas;
		this.cprovmas = cprovmas;
		this.provmenos = provmenos;
		this.cprovmenos = cprovmenos;
	}
	
	public Funcionamiento() {
		
		this.semana = 0;
		this.prodmas = null;
		this.cpmas = null;
		this.prodmenos = null;
		this.cpmenos = null;
		this.provmas = null;
		this.cprovmas = null;
		this.provmenos = null;
		this.cprovmenos = null;
	
	}

	public int getSemana() {
		return semana;
	}

	public void setSemana(int semana) {
		this.semana = semana;
	}

	public Long getProdmas() {
		return prodmas;
	}

	public void setProdmas(Long prodmas) {
		this.prodmas = prodmas;
	}

	public Long getCpmas() {
		return cpmas;
	}

	public void setCpmas(Long cpmas) {
		this.cpmas = cpmas;
	}

	public Long getProdmenos() {
		return prodmenos;
	}

	public void setProdmenos(Long prodmenos) {
		this.prodmenos = prodmenos;
	}

	public Long getCpmenos() {
		return cpmenos;
	}

	public void setCpmenos(Long cpmenos) {
		this.cpmenos = cpmenos;
	}

	public Long getProvmas() {
		return provmas;
	}

	public void setProvmas(Long provmas) {
		this.provmas = provmas;
	}

	public Long getCprovmas() {
		return cprovmas;
	}

	public void setCprovmas(Long cprovmas) {
		this.cprovmas = cprovmas;
	}

	public Long getProvmenos() {
		return provmenos;
	}

	public void setProvmenos(Long provmenos) {
		this.provmenos = provmenos;
	}

	public Long getCprovmenos() {
		return cprovmenos;
	}

	public void setCprovmenos(Long cprovmenos) {
		this.cprovmenos = cprovmenos;
	}

	@Override
	public String toString() {
		return "Funcionamiento [semana=" + semana + ", prodmas=" + prodmas + ", cpmas=" + cpmas + ", prodmenos="
				+ prodmenos + ", cpmenos=" + cpmenos + ", provmas=" + provmas + ", cprovmas=" + cprovmas
				+ ", provmenos=" + provmenos + ", cprovmenos=" + cprovmenos + "]";
	}
	
	
	
	
	

}
