package forms;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "donem", schema = "public")
public class Donem implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="donem_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="baslama_tarihi")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date baslamaTarihi;
	@Column(name="bitis_tarihi")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date bitisTarihi;
	
	public Donem(){
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public Date getBaslamaTarihi() {
		return baslamaTarihi;
	}
	public void setBaslamaTarihi(Date baslamaTarihi) {
		this.baslamaTarihi = baslamaTarihi;
	}
	public Date getBitisTarihi() {
		return bitisTarihi;
	}
	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}	
}