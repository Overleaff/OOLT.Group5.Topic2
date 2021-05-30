package model.algorithm;

import model.individual.BackPack;
import model.individual.Element;
import model.individual.Individual;
import model.individual.PoolElements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GeneticAlgorithm extends HeuristicAlgorithm {

	private ArrayList<Individual>  population;

	public GeneticAlgorithm(){
		this.population = (ArrayList<Individual>) getPopulation();
	}

	public Individual doOtherSteps() {
		// sort individual theo fitness
		Collections.sort(
				population, Comparator.comparingDouble(Individual::fitness)
		);
		for(int i = 0; i < NUM_INDIVIDUAL/2; i++)
			crossover((BackPack) population.get(i), (BackPack) population.get(NUM_INDIVIDUAL-i-1));
		for(int i = 0; i< NUM_INDIVIDUAL/2; i++)
			mutate(population.get(i));
		System.out.println(getBestIndividual() + " "+getBestIndividual().getWeight());
		return getBestIndividual();
	}

	public void crossover(BackPack bp1, BackPack bp2) {
		// swap half of elements in in1 with half of elements in in2, number of elements in in1 equals to in2
		Element[] items1 = bp1.getElements();
		Element[] items2 = bp2.getElements();
		int i;
		double tmpWei;
		String tmpString;
		for(i = 0; i < bp1.getNumOfElement(); i++) {
			if (!bp1.isContain(items2[i]) && !bp2.isContain(items1[i])) {
				tmpWei = items1[i].getWeight();
				tmpString = items1[i].getImageFile();
				bp1.updateElement(i, items2[i].getWeight(), items2[i].getImageFile());
				bp2.updateElement(i, tmpWei, tmpString);
			}
		}
	}


	public void mutate(Individual bp) {
		// update randomly 1/10 elements in the backpack with the new element not in backpack before
		int ran = (int)(Math.random() * 10);
		Element[] elements = bp.getElements();
		Element e = bp.getNewRandomElement();
		bp.updateElement(ran, e.getWeight(), e.getImageFile());
	}

	public static void main(String[] args){
		GeneticAlgorithm gA = new GeneticAlgorithm();
		Element[] elements = new PoolElements().getElements();
		gA.solve(elements);
	}
}
