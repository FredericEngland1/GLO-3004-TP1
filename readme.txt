COMMENT EXECUTER LE CODE :
    En théorie il suffi simplement de compiler le code Java et de lancer l'application résultante pour exécuter le code. L'utilisation
    d'un IDE est recommendé car cela simplifie le processus. Nous assumons que le correcteur à des connaissances de base en programmation
    et sera en mesure d'exécuter les code avec les étape ci-dessus.

AVANT DE COMMENCER :
    Avant de démarrer la Confiturerie, il est important de bien sélectionner le nombre de bocaux, valves et etiqueteuses qui seront utilisés.
    Ces nombres correspondent à N, V et E respectivement. Il est aussi recommender de mettre un temps de sommeil (millisecondes) relativement long
    afin de ralentir la vitesse d'écriture dans la console. Cela permettra de mieux suivre le déroulement des processus car l'utilisateur devra
    interagir avec la simulation. Le temps de sommeil peut être ajusté en cours de simulation à l'aide du bouton "MAJ Temps Sommeil" (MAJ = Mise à jour).

POUR COMMENCER :
    Une fois les variables de base établient, on peut débuter la simulation avec le bouton "Commencer la simulation" (La modification des variables autre
    que le temps de sommeil ne sera pas prise en compte a moins d'arrêter la Confiturerie et de la redémarrer).

POUR ARRETER :
    Une fois la simulation débuté, il est possible de l'arrêter complètement avec le bouton "Arret". Au redémarrage, le console du UI sera vidé.

PENDANT LA SIMULATION :
    À n'importe quel moment pendant la simulation, il est possible de la mettre sur pause en appuyant sur le bouton "Pause".
    Pour reprendre la simulation il suffi d'appuyer sur le bouton "Redemarrer". Il est à noter que si le bouton de pause
    est cliqué alors qu'un processus exécute déjà une étape dans son processus, celui-ci va terminer son étape avant de tomber en pause.

RUPTURE ET APPROVISIONNEMENT :
    Pour faciliter le travail du correcteur (oui toi!) nous avons décidé que la rupture et l'approvisionnement allaient être contrôlé
    par nulle autre que lui-même. Pour créer une rupture, il faut d'abord sélectionner le type de bocal qui subira la rupture. Ceci
    doit être fait avec la liste déroulante "Type". Ensuite il faut appuyer sur le bouton "Rupture". Tant que l'utilisateur n'aura
    pas fait de réapprovisionnement, ce type de bocal restera en rupture. Pour effectuer un approvisionnement, il faut sélectionner
    le type de bocal à approvisionner de la même manière que pour la rupture. Il suffi ensuite d'appuyer sur le bouton "Approvisionnement".
    Créer une rupture pour un type déjà en rupture ou un approvisionnement pour un type qui n'est pas en rupture n'aura aucun effet.

COMMENT INTERPRETER LES SORTIES :
    Au centre de l'interface utilisateur se trouve une "console" personnalisée dans laquelle la simulation va écrire les étapes
    accomplient par les processus au cours de leur exécution. Ces lignes aurons la forme :

        "Le bocal [Type].[ID] {a {commence; termine} {le remplissage; l'etiquetage}; est pret !}"
            ou
        "{Rupture; Approvisionnement} : [Type]"

    Lorsque l'utilisateur commence la simulation, "Debut" sera affiché.
    Lorsqu'il va cliquer sur Pause, "Pause" sera affiché.
    Lorsqu'il va appuyer sur Redemarrer, "Redemarrage" sera affiché.
    Lorsqu'il va appuyer sur Arret, "Arret" sera affiché.
    Finalement, lorsqu'il va cliquer sur MAJ Temps Sommeil, "Temps Sommeil : [TempsSommeil]" sera affiché.

TRUCS ET ASTUCES :
    Pour faciliter le controle de la trace, il est recommendé de mettre un temps de sommeil suffisament long polur laisser le temps à
    l'utilisateur de réagir aux écritures dans la console. Lorsque celui-ci pense que le moment est le bon pour effectuer un rupture
    ou un approvisionnement, il est recommendé de d'abord faire pause et de s'assurer qu'après l'exécution des processus en cours,
    le moment est toujours le bon, de créer la rupture ou l'approvisionnement, puis de redémarrer les processu. Il est à noter qu'il
    est possible d'effectuer la rupture ou l'approvisionnement pendant que la simulation est en pause et que cela n'aura aucun impacte
    sur le déroulement de la simulation.

PETIT BONUS :
    Notre code supporte l'ajout d'une infinité de type de bocaux. Il n'implémente que A et B dans le cadre de ce projet, mais si
    l'utilisateur le souhaite, il peut ajouter d'autre types (tel que C, D, E, etc...) dans l'Enum "TypeBocal" avant de compiler
    et lancer l'application, et tous devrait fonctionné tel qu'attendu.

DETAIL D'IMPLEMENTATION :
