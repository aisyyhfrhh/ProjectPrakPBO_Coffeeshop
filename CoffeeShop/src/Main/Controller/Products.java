package Main.Controller;

public class Products {
	private String idKopi, namaKopi;
	private int stok, harga;
	private static int total;

    public String getIdKopi(){ return idKopi;}
	public String getNamaKopi(){
			return namaKopi;
	}
	public int getHarga(){ return harga; }
	public int getStok(){
			return stok;
	}

	public void setIdKopi(String vidKopi){ idKopi = vidKopi;}
	public void setNamaKopi(String vnamaKopi){
			namaKopi = vnamaKopi;
	}
	public void setHarga(int vHarga){
			harga = vHarga;
	}
	public void setStok(int vstok){ stok = vstok; }

	public static int getTotal(){ return total;}
	public static void setTotal(int vtotal){total += vtotal;}


}