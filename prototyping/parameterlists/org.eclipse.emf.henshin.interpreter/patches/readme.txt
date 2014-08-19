DC: (Dangling Constraint)
  Type: Workaround/Bugfix?
  Description:
    (1) falsche gecachte Werte für eingehende Referenzen => ignoriere Generics
	(2) nicht beachten: derived, unchangeable und transient references.
	
Reverse:
  Type: Bugfix
  Description: 
    attribute kann null sein, Prüfung auf null ist jedoch zu spät.
    (-> NullPointerException)