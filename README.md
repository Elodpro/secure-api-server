🛡️ Serveur Web Sécurisé : Conception et Déploiement d'une API Authentifiée (Java/Spring Boot)
# Serveur-Web-Sécurisé
Ce projet est un démonstrateur d'architecture backend moderne, focalisé sur la sécurité par conception et l'industrialisation. Il vise à concevoir, déployer et durcir un service web complet exposé via HTTPS et protégé par une authentification robuste par jetons.

# Objectifs Clés du Projet |
Nous avons combiné trois axes fondamentaux pour garantir un service de référence : le développement, l'infrastructure, et la cybersécurité.


# Backend Sécurisé (Java/Spring Boot)  :


Conception d'un backend HTTP exposant des ressources publiques et privées.

Gestion de l'authentification et de l'autorisation par rôles via des jetons JWT (JSON Web Token).


Politique Stateless (sans état) côté application.



# Infrastructure Sécurisée (Système/Réseau)  :

Exposition obligatoire via HTTPS partout.

Intégration derrière un Reverse Proxy standard pour la terminaison TLS et l'application des politiques de sécurité.



# Durcissement & Observabilité  :

Application des En-têtes de sécurité HTTP essentiels au niveau du frontal.


Hachage des secrets via un algorithme résistant (ex: bcrypt ou Argon2).


Mise en place de la Journalisation structurée et des indicateurs de santé pour le diagnostic.

# Tests et Qualité |
Le projet inclut un plan de tests rigoureux couvrant :

Les tests unitaires sur la logique d'authentification/autorisation.

Les scénarios de sécurité (jeton absent, jeton expiré, rôle insuffisant).

# secure by design
La vérification des critères d'acceptation non fonctionnels (HTTPS obligatoire, en-têtes de sécurité actifs).

Ports :

Nginx : 443

Spring Boot : 8080

Schéma :

Client → Nginx (HTTPS, headers, rate limit, CORS) → Spring Boot (API secure).

Lister les types d’endpoints :

/public/** : libre

/auth/** : login / refresh

/api/** : protégé JWT + rôles

/actuator/** : restreint (admin / internal)
