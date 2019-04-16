#!/bin/bash

if [ -z $1 ]	
then
	echo "Veuillez indiquer le nom du fichier a transformer en pdf"

else

	if [ -f ~/.bash_aliases ]; then
    		latex $1.tex
		dvips $1.dvi
		ps2pdf $1.ps
		rm $1.aux
		rm $1.dvi
		rm $1.log
		rm $1.out
		rm $1.ps
		echo "Création du pdf terminé"
	else
		echo "Impossible de trouver le .tex"
	fi
fi
exit 0

