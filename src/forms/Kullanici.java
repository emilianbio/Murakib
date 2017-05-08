package forms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "kullanici", schema = "public")
public class Kullanici implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "tabloSequnce", sequenceName = "kullanici_id_seq")
	@GeneratedValue(generator = "tabloSequnce")
	private long id;
	@Column(name = "isim")
	private String isim;
	@Column(name = "sifre")
	private String sifre;
	@Column(name = "pasif")
	private Boolean pasif;
	@Column(name = "selahiyet")
	private Boolean selahiyet;
	@Column(name = "ext_selahiyet")
	private Boolean extSelahiyet;
	@Column(name = "email")
	private String email;
	@Column(name = "telefon")
	private String telefon;
	@ManyToOne
	@JoinColumn(name = "grub_id")
	private Grub grub;

	public Kullanici() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Grub getGrub() {
		return grub;
	}

	public void setGrub(Grub grub) {
		this.grub = grub;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public Boolean getPasif() {
		return pasif;
	}

	public void setPasif(Boolean pasif) {
		this.pasif = pasif;
	}

	public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public Boolean getSelahiyet() {
		return selahiyet;
	}

	public void setSelahiyet(Boolean selahiyet) {
		this.selahiyet = selahiyet;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Boolean getExtSelahiyet() {
		return extSelahiyet;
	}

	public void setExtSelahiyet(Boolean extSelahiyet) {
		this.extSelahiyet = extSelahiyet;
	}

}
