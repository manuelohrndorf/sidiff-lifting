DanglingConstraint:
  Type: Workaround/Bugfix?
  Description:
    (1) falsche gecachte Werte für eingehende Referenzen => ignoriere Generics
	(2) nicht beachten: derived, unchangeable und transient references.
	
ReverseRuleApplication:
  Type: Bugfix
  Description: 
    attribute kann null sein, Prüfung auf null ist jedoch zu spät.
    (-> NullPointerException)
     
MultiPreMatches [Not yet applied]:
  Type: New Henshin Feature
  Description:
    Multi-rules can be executed with extended "Multi-Pre-Matches"
    This was formerly called the Henshin "parameter lists" feature
    
PathPAC:
  Type: Bugfix/Workaround?
  Description:
    Pfadberechnung versucht sich von einer NAC/PAC nach oben zu hangeln, auf den "echten Knoten".
    Das funktioniert aber nicht wenn der Konten selbst ein echter NAC/PAC Knoten ist.