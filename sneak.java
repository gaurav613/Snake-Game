package snakegame;

import java.awt.Dimension;

import javax.swing.JFrame;

public class sneak
{
	public static void main(String args[]) {
		JFrame frame = new JFrame("Snake");
		frame.setContentPane(new Gpanel());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setPreferredSize(new Dimension(Gpanel.WIDTH,Gpanel.HEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}