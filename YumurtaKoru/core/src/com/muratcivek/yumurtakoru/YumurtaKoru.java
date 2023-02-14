package com.muratcivek.yumurtakoru;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.DisplayMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import jdk.internal.org.jline.utils.Display;

public class YumurtaKoru extends ApplicationAdapter {
	SpriteBatch spritebatch;
	Texture arkaplan;
	Texture yumurta;
	Texture engel1;
	Texture engel2;
	Texture engel3;
	int yumurtaYekseni;
	int yumurtaXekseni;
	int baslamaKontrol;
	int rekor=0;
	int engelGidis=1500;
	float randomSayi;
	float randomSayi2;
	float randomSayi3;
	Random rastgele;
	Circle yumurtaCircle;
	Circle engel1Circle;
	Circle engel2Circle;
	Circle engel3Circle;
	ShapeRenderer shaperanderer;
	ShapeRenderer shaperanderer2;
	ShapeRenderer shaperanderer3;
	ShapeRenderer shaperanderer4;;
	BitmapFont rekorFont;
	BitmapFont oyunSonuFont;

	@Override
	public void create () {
		spritebatch = new SpriteBatch();
		arkaplan = new Texture("arkaplan.png");
		yumurta = new Texture("yumurta1.png");
    	engel1 = new Texture("engel1.png");
		engel2 = new Texture("engel1.png");
		engel3 = new Texture("engel1.png");
		yumurtaYekseni = Gdx.graphics.getHeight()/2 - Gdx.graphics.getHeight()/5;
		yumurtaXekseni = Gdx.graphics.getHeight()/2;
		baslamaKontrol =2;
		rastgele =  new Random();
		randomSayi = (rastgele.nextFloat()) * (Gdx.graphics.getHeight())-300;
		randomSayi2 = (rastgele.nextFloat()) * (Gdx.graphics.getHeight())-300;
		randomSayi3 = (rastgele.nextFloat()) * (Gdx.graphics.getHeight())-300;
		yumurtaCircle = new Circle();
		engel1Circle = new Circle();
		engel2Circle = new Circle();
		engel3Circle = new Circle();
		shaperanderer = new ShapeRenderer();
		shaperanderer2 = new ShapeRenderer();
		shaperanderer3 = new ShapeRenderer();
		shaperanderer4 = new ShapeRenderer();
		oyunSonuFont = new BitmapFont();
		oyunSonuFont.setColor(Color.BLACK);
		oyunSonuFont.getData().setScale(4);
		rekorFont = new BitmapFont();
		rekorFont.setColor(Color.BROWN);
		rekorFont.getData().setScale(5);
	}

	@Override
	public void render () {
		spritebatch.begin();

//arka planı ve rekorumuzu ekrana çiziyoruz
		spritebatch.draw(arkaplan,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		rekorFont.draw(spritebatch," " + rekor,25,75);

//kullanıcı ekrana tıklarsa oyunu başlatıyoruz
		if(Gdx.input.justTouched()) {
			baslamaKontrol = 1;
			//her tıkladığında yumurtaya yukarı hareketini veriyoruz
			if(Gdx.input.justTouched()){
				yumurtaYekseni = yumurtaYekseni + 150;
			}
		}

//oyun başlayınca olacakları yazıyoruz
		if(baslamaKontrol ==1) {
			//yumurtayı ve engelleri çiziyoruz
			spritebatch.draw(yumurta, yumurtaXekseni, yumurtaYekseni, 150, 150);
			spritebatch.draw(engel1, engelGidis, randomSayi, 400, 300);
			spritebatch.draw(engel2, engelGidis, randomSayi2, 400, 300);
			spritebatch.draw(engel3, engelGidis, randomSayi3, 400, 300);
			while (engelGidis < 0) {
				//engeller ekrandan çıkınca tekrar çizdiriyoruz y ekseni random olacak şekilde
				randomSayi = (rastgele.nextFloat()) * (Gdx.graphics.getHeight()) - 300;
				randomSayi2 = (rastgele.nextFloat()) * (Gdx.graphics.getHeight()) - 300;
				randomSayi3 = (rastgele.nextFloat()) * (Gdx.graphics.getHeight()) - 300;
				spritebatch.draw(engel1, 100, randomSayi, 400, 300);
				spritebatch.draw(engel2, 200, randomSayi2, 400, 300);
				spritebatch.draw(engel3, 300, randomSayi3, 400, 300);
				//engeller kaybolunca yeni engellerin çıkacağı x eksenini belirliyoruz
				engelGidis = 1500;
				//rekor sayacımız
				rekor++;
			}
			//yumurtaya düşürme hareketi veriyoruz
			yumurtaYekseni = yumurtaYekseni - 6;
			//engellere yumurtaya doğru gelme hareketi veriyoruz
			engelGidis = engelGidis - 8;
		}

//yumurtaya zarar gelirse baslamaKontrol =0 olacaktır. O esnada olacakları yazıyoruz
		if(baslamaKontrol==0){
			oyunSonuFont.draw(spritebatch,"Yumurtayi kirdin! Tekrar korumak icin tikla.",
					Gdx.graphics.getWidth()/4,
					70);
		}

//Çarpışmayı algılamak için yumurta ve engellere şekiller çizdik
		yumurtaCircle.set(yumurtaXekseni +75 ,yumurtaYekseni + 75 ,75);
		/*	shaperanderer.begin(ShapeRenderer.ShapeType.Filled);
		shaperanderer.setColor(Color.BLUE);
		shaperanderer.circle(yumurtaCircle.x,yumurtaCircle.y,yumurtaCircle.radius);
		shaperanderer.end();*/

		engel1Circle.set(engelGidis +120,randomSayi+150 ,30);
		/*shaperanderer2.begin(ShapeRenderer.ShapeType.Filled);
		shaperanderer2.setColor(Color.BLACK);
		shaperanderer2.circle(engel1Circle.x,engel1Circle.y,engel1Circle.radius);
		shaperanderer2.end();*/

		engel2Circle.set(engelGidis +120,randomSayi2+150 ,30);
		/*shaperanderer3.begin(ShapeRenderer.ShapeType.Filled);
		shaperanderer3.setColor(Color.GRAY);
		shaperanderer3.circle(engel2Circle.x,engel2Circle.y,engel2Circle.radius);
		shaperanderer3.end();*/

		engel3Circle.set(engelGidis +120,randomSayi3+150 ,30);
		/*shaperanderer4.begin(ShapeRenderer.ShapeType.Filled);
		shaperanderer4.setColor(Color.GOLD);
		shaperanderer4.circle(engel3Circle.x,engel3Circle.y,engel3Circle.radius);
		shaperanderer4.end(); */

//Yumurta engellere çarparsa yada ekranın üstüne, altına çıkarsa oyunu bitirme durumu oluşacaktır.
		if(Intersector.overlaps(yumurtaCircle,engel1Circle) ||
				Intersector.overlaps(yumurtaCircle,engel2Circle) ||
				Intersector.overlaps(yumurtaCircle,engel3Circle) ||
				yumurtaYekseni<0 || yumurtaYekseni>Gdx.graphics.getHeight()){
			engelGidis=1500;
			rekor=0;
			yumurtaYekseni = Gdx.graphics.getHeight()/2 - Gdx.graphics.getHeight()/5;
			baslamaKontrol =0;
			//kullanıcı oyuna tekrar başlamak isterse ilk engellerin tekrar random gelmesini sağlıyoruz
			randomSayi = (rastgele.nextFloat()) * (Gdx.graphics.getHeight()) - 300;
			randomSayi2 = (rastgele.nextFloat()) * (Gdx.graphics.getHeight()) - 300;
			randomSayi3 = (rastgele.nextFloat()) * (Gdx.graphics.getHeight()) - 300;
			}

		spritebatch.end();
		}

	@Override
	public void dispose () {

	}
}
