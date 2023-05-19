**Clean archi Kata:** 

- Si le but de l'application, c'est de faire de l'authentification alors dans le use-case d'authentification, il y la logique du user name inconnu et du mot de passe incorrect.
  (Je me suis posée la question et d'ailleurs au départ, j'avais mis la logique côté repo avec juste une méthode exists côté domain)
- Côté exposition Rest : J'ai fait un truc un peu overkill selon moi : De la validation via Json-schema
  - Pourquoi : 
    - Pour éviter d'avoir les annotations de validation json dans l'object de mon domain ou 
    - de devoir créer une "DTO" de user identique à celle du domain avec un mapper
  - Les avantages que j'y ai vu : 
    - Les librairies java existante sont multiples pour le faire, celle utilisé json-skema est bien lisible dans son implem
    - Permet de gérer des versions de schema différents pour faire cohabiter deux version d'api le temps de la migration des consommateurs
    - Pouvoir avoir une description précise du json que l'on souhaite recevoir, partageable avec ses consommateurs.
  - Les désavantages : 
    - On délégue a ce validateur toute la validation de notre json, donc plus de mapping automatique en objet via jackson
    - Sur l'input, on est sur une string, on pourrait nous envoyer n'importe quoi, après le validateur est là pour ça. 
    - Je n'ai pas voulu pousser plus loin, mais finalement, est-ce que ce validateur ne devrait pas faire parti d'un object mapper custom pour User (bon à cette étape, c'est de l'over-engineering^^)
