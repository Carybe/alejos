import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;

import lejos.robotics.mapping.LineMap;
import lejos.geom.Rectangle;
import lejos.geom.Line;
import lejos.robotics.navigation.Pose;
import java.util.Random;
import lejos.robotics.navigation.Move;

// MAC RADAR: 00:16:53:1B:69:93


public class Master {
	private static final byte ROTATE = 0;
	private static final byte ROTATETO = 1;
	private static final byte RANGE = 2;
	private static final byte STOP = 3;
	private static final byte FORWARD = 4;
	private static final float angleNoise = 15f;
	private static final float distanceNoise = 5.0f;

	private NXTComm nxtComm;
	private DataOutputStream dos;
	private DataInputStream dis;

	public int M = 10000;
	public Random rand;
	public Pose[] particles;
}

	// NXT BRICK ID
	private static final String NXT_ID = "NXT2";	
	
	public Master {
		particles = new Pose[M];
		rand new Random();
		for (int i=0; i < M; i++) {
 			particles[i] = new Pose(WIDTH*rand.nextFloat(), // x
 			HEIGHT*rand.nextFloat(), // y
 			rand.nextInt(360) // theta
 		);
	}

	private float sendCommand(byte command, float param) {
		try {
			dos.writeByte(command);
			dos.writeFloat(param);
			dos.flush();
			return dis.readFloat();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return -1;
		}
	}
	
	/**
	 * Connect to the NXT
	 *
	 */
	private void connect() {
		try {
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Blluetooth communication */
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);			
			NXTInfo[] nxtInfo = nxtComm.search(Master.NXT_ID);
			
			if (nxtInfo.length == 0) {
				System.err.println("NO NXT found");
				System.exit(1);
			}
			
			if (!nxtComm.open(nxtInfo[0])) {
				System.err.println("Failed to open NXT");
				System.exit(1);
			}
			
			dis = new DataInputStream(nxtComm.getInputStream());
			dos = new DataOutputStream(nxtComm.getOutputStream());
			
		} catch (NXTCommException e) {
			System.err.println("NXTComm Exception: "  + e.getMessage());
			System.exit(1);
		}
	}
	/**
	 * Terminate the program and send stop command to the robot
	 *
	 */
	private void close() {
		try {
			dos.writeByte(STOP);
			dos.writeFloat(0f);
			dos.flush();
			Thread.sleep(200);
			System.exit(0);
		} catch (Exception ioe) {
			System.err.println("IO Exception");
		}
	}		
	
	static LineMap makeMap(Line [] lines){
		float limit;
		limit=220;

		//Mapa
		lines[0] = new Line(0,0,155,0);
		lines[1] = new Line(155,0,155,130);
		lines[2] = new Line(155,130,315,130);
		lines[3] = new Line(315,130,315,226);
		lines[4] = new Line(315,226,58,226);
		lines[5] = new Line(58,226,0,152);
		lines[6] = new Line(0,152,0,0);
		
		//Quadrado
		lines[7] = new Line(55,90,75,90);
		lines[8] = new Line(75,90,75,70);
		lines[9] = new Line(75,70,55,70);
		lines[10] = new Line(55,70,55,90);

		return new LineMap(lines,new Rectangle(0,0,limit,limit));
	}

	static float norm(float x, float y){
		return (float) Math.exp(- Math.pow(x-y,2)/5.5);
	}

	// Atualiza crença após executar a e medir z
	public Pose[] ParticleFilter (Pose[] particles, float a, float z) {
		// atualizar amostras
		Pose[] tmpParticles = new Pose[M];
		double[] weights = new double[M];
		double sum = 0.0; // estimativa de p(z)
		for (int i=0; i < M; i++) {
			tmpParticles[i] = sampleMotionModel(particles[i], a); // predição
			weights[i] = measurementModel(z, tmpParticles[i]); // correção
			sum += weights[i];
		}
		// gerar novas amostras
		Pose[] newParticles = new Pose[M];
		for (int i=0; i < M; i++) {
			int j = sampleDiscreteDistribution(weights,sum);
			newParticles[i] = tmpParticles[j];
		}
		return newParticles;
	}

	// modelo de ação
	// assume move.type = Move.MoveType.TRAVEL | Move.MoveType.ROTATE
	public Pose sampleMotionModel(Move move, Pose pose) {
		Pose newPose = new Pose();
		if (move.type == Move.MoveType.TRAVEL) {
		 // translação

			double d = move.getDistanceTraveled() + distanceNoise*rand.nextGaussian();
			double a = pose.getHeading() + angleNoise*rand.nextGaussian();

			Point p = pose.pointAt(d,a);
			newPose.translate(p.getX()-pose.getX(), p.getY()-pose.getY());
		} else {
		 // rotação

			double a = (pose.getHeading() + move.getAngleTurned() + angleNoise*rand.nextGaussian());
			newPose.setHeading((int)(pose.getHeading()+move.getAngleTurned()+0.5) % 360);

			//newPose.setHeading((pose.getHeading()+move.getAngleTurned()) % 360);
		}
		return newPose;
	}

	// modelo de percepção
	public double measurementModel(Pose pose, float angle) {
	// modelo do sonar
	}

	// gera inteiro 0 ≤ j < length
	public double sampleDiscreteDistribution(double[] pmf, double norm) {
		double u = rand.nextDouble(); // gerar número aleatório em (0,1)
		double cdf = 0.0; // densidade de probabilidade cumulativa
		int j;
		for (j=0; j < pmf.length; j++) // soma{pmf[0..j-1]} ≤ u < soma{pmf[0..j]}
			cdc += pmf[j]/norm;
			if (cdf > u) break;
		}
		return j;
	}

	private updatePose(){
		// localização - postura média
		Pose pose = new Pose(0,0,0);
		for (int i=0; i < M; i++) {
			pose.setX(pose.getX()+particles[i].getX());
			pose.setY(pose.getY()+particles[i].getY());
			pose.setHeading(pose.getHeading()+particles[i].getHeading());
		}
		pose.setX() = pose.getX()/M;
		pose.setY() = pose.getY()/M;
		pose.setHeading() = pose.getHeading()/M;

		
	}

	public static void main(String[] args) {
		int n;
		byte cmd = 0; float param = 0f; float ret=0f; float z=0f;
		master.connect();

	    Scanner scan = new Scanner( System.in );	    
		Master master = new Master();
		Line[] lines = new Line[9];
		LineMap map = makeMap(lines);

		// localização - região
		Rectangle rect = new Rectangle(0,0,0,0);
		for (int i=0; i < M; i++) {
 			if (rect.x > particles[i].getX()) { rect.x = particles[i].getX(); }
			if (rect.x + rect.w < particles[i].getX()) { rect.w = particles[i].getX()-rect.x; }
			if (rect.y > particles[i].getY()) { rect.y = particles[i].getY(); }
			if (rect.y+rect.h < particles[i].getY()) { rect.h = particles[i].getY()-rect.y; }
		}
		
	}
}