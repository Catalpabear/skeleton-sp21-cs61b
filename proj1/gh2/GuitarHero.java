package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {

    public static final double CONCERT_A = 440.0;
// qwertyuiop[
// 2457689-=
// dfgjk;'
// zxcvbnm,./
// " "
    public static void main(String[] args) {

        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] guitarHeroes = new GuitarString[37];
        for (int i = 0; i < guitarHeroes.length; i++) {
            double frequency = CONCERT_A * Math.pow(2, (i-24)/ 12.0);
            guitarHeroes[i] = new GuitarString(frequency);
        }
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyCode = keyboard.indexOf(key);
                if (keyCode >=0) {
                    guitarHeroes[keyCode].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample=0.0;
            for (int i = 0; i < guitarHeroes.length; i++) {
                sample += guitarHeroes[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < guitarHeroes.length; i++) {
                guitarHeroes[i].tic();
            }
        }
    }
}
