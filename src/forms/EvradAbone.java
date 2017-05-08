package forms;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "evrad_abone", schema = "public")
public class EvradAbone implements java.io.Serializable {
	private static final long serialVersionUID = 4418029727139184238L;
	@Id
    @Column(name = "id",nullable=false)		
	@SequenceGenerator(name="tabloSequnce", sequenceName="evrad_abone_id_seq")	
	@GeneratedValue(generator="tabloSequnce")
	private long id;
	@Column(name="baslama_tarihi") 
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date baslamaTarihi;
	@Column(name="pasif")
	private Boolean pasif;	
	@Column(name="kayit_tarihi")
	private Date kayitTarihi;
	@Column(name="aciklama")
	private String aciklama;
	@Column(name="hususi")
	private Boolean hususi;
	@Column(name="parca_suresi")
	private Short parcaSuresi;
	@Column(name="sabit")
	private Boolean sabit;
	
	@ManyToOne
	@JoinColumn(name="evrad_kisim_id")
    private EvradKisim evradKisim;
	@ManyToOne
	@JoinColumn(name="grub_id")
    private Grub grub;
	@ManyToOne
	@JoinColumn(name="kullanici_id")
    private Kullanici kullanici;
	@ManyToOne
	@JoinColumn(name="kaydeden_kullanici_id")
    private Kullanici kaydedenKullanici;
	
	public EvradAbone() {
	}

	public EvradAbone(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public Date getKayitTarihi() {
		return kayitTarihi;
	}

	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}

	public Date getBaslamaTarihi() {
		return baslamaTarihi;
	}

	public void setBaslamaTarihi(Date baslamaTarihi) {
		this.baslamaTarihi = baslamaTarihi;
	}

	public Kullanici getKaydedenKullanici() {
		return kaydedenKullanici;
	}

	public void setKaydedenKullanici(Kullanici kaydedenKullanici) {
		this.kaydedenKullanici = kaydedenKullanici;
	}

	public Boolean getPasif() {
		return pasif;
	}

	public void setPasif(Boolean pasif) {
		this.pasif = pasif;
	}
	
	public EvradKisim getEvradKisim() {
		return evradKisim;
	}

	public void setEvradKisim(EvradKisim evradKisim) {
		this.evradKisim = evradKisim;
	}

	public Grub getGrub() {
		return grub;
	}

	public void setGrub(Grub grub) {
		this.grub = grub;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public Boolean getHususi() {
		return hususi;
	}

	public void setHususi(Boolean hususi) {
		this.hususi = hususi;
	}

	public Short getParcaSuresi() {
		return parcaSuresi;
	}

	public void setParcaSuresi(Short parcaSuresi) {
		this.parcaSuresi = parcaSuresi;
	}

	public Boolean getSabit() {
		return sabit;
	}

	public void setSabit(Boolean sabit) {
		this.sabit = sabit;
	}
	
}
