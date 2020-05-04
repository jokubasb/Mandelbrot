#!/bin/sh
#SBATCH -p short # eilė
#SBATCH -N1 # keliuose kompiuteriuose (trečioje programoje nenaudojame MPI, todėl tik 1)
#SBATCH -c12 # kiek procesorių viename kompiuteryje
#SBATCH -C alpha # telkinys (jei alpha neveikia, bandykite beta)
java com.parallel.Main 2 40000 # jūsų programos įjungimas
