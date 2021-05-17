# Blobs aka Robki

Zaimplementowane rozwiązanie jest podstawową, bez rozszerzeń warunków symulacji, wersją
zadania, z dołączoną graficzną reprezentacją korzystając z JavaFX. Pomimo że rozwiązanie
nie posiada rozszerzeń, zostało pomyślane właśnie o nich. Moim celem było umożliwienie
dosyć prostego dodawania nowych własności i zachowań nie tylko robów, które dalej będę
nazywał blobkami, ale też jedzenia, czy dowolnych nowych agentów, którzy mogą pojawić się
w symulacji.

## Kompilacja i uruchamianie

Program testowałem pod windowsem z użyciem:

```
openjdk version "15.0.2" 2021-01-19
OpenJDK Runtime Environment (build 15.0.2+7-27)
OpenJDK 64-Bit Server VM (build 15.0.2+7-27, mixed mode, sharing)

```

Ponieważ program używa javafx, należy dołączyć odpowiedni moduł (znajduje się w
folderze ```libs```). Poniższe komendy są do uruchomienia z folderu ```src```

```
javac --module-path ../libs/javafx-sdk-11.0.2/lib --add-modules javafx.controls zad1/Symulacja.java
```

```
java --module-path ../libs/javafx-sdk-11.0.2/lib --add-modules javafx.controls zad1.Symulacja ../resources/plansza.txt ../resources/parametry.txt
```

## Entity Component System

Zdecydowałem się zaimplementować Entity Component System z kilku powodów:

1. Pozwala na oddzielenie różnych części programu, np. logikę symulacji od grafiki.
2. Umożliwia recykling różnych kawałków kodu w dość intuicyjny sposób.
3. Pozwala na proste dodawanie nowych zachowań i własności agentom świata.
4. Jest dosyć skomplikowany i to zadanie było wymówką, aby napisać to rozwiązanie w javie.

W kilka podstawowych pojęć:

- **Komponent (Component)** - Element przechowujący dane agenta, np. PositionComponent
  przechowuje pozycję agenta.
- **Tag** - Pomocniczy, pusty, komponent. Używany tylko i wyłącznie do rozróżniania dwóch
  agentów od siebie, jeśli powinni zachowywać się inaczej, ale mają taki sam zestaw
  komponentów.
- **Agent (Agent)** - Agent jest obiektem świata, który przechowuje zestaw komponentów i
  tagów.
- **System (EngineSystem)** - Funkcja, która wywołuje się tylko i wyłącznie na agentach
  zawierających wymagane komponenty i tagi.
- **Akcja (Action)** - Zdarzenie, które jest wysyłana do wszystkich komponentów danego
  agenta. To, co każdy z komponentów z nim zrobi, to sprawa komponentu.
- **Fabryka (Factory)** - Nie koniecznie ściśle powiązane z ECS, jednak wykorzystywany u
  mnie. Z zestawu komponentów-schematów, tworzy nowego agenta. Można przekazać mu
  argumenty tworzące agenta.
- **Menadżer (Engine)** Trzyma i zarządza wszystkimi agentami, ale także systemami. Raz na
  każdy update wywołuje wszystkie systemy na wszystkich komponentach, które zawierają
  wymagane komponenty i tagi.

W aktualnym stanie rozwiązania, tagi są zaimplementowane, ale nie są używane.

Przykłady użycia (wzięte z kodu źródłowego):

**Tworzenie nowego ECS**

```java
Engine engine = new Engine(
        BlobComponent.class,
        DirectionComponent.class,
        FoodComponent.class,
        GraphicComponent.class,
        PositionComponent.class,
        ReplicableComponent.class,
        ColorComponent.class
);

engine.addSystem(new UpdateFood(engine));
engine.addSystem(new UpdateBlob(engine));
engine.addSystem(new ReplicateBlob(engine));
engine.addSystem(new UpdateGraphics(engine));
engine.addSystem(new LogUpdateSystem(engine));
```

**System**

```java
public class UpdateBlob extends EngineSystem{
	public UpdateBlob(Engine engine){
		super(engine, BlobComponent.class, PositionComponent.class, DirectionComponent.class);
	}
	
	@Override
	public void execute(Agent agent){
		BlobComponent blob = agent.getComponent(BlobComponent.class);
		PositionComponent position = agent.getComponent(PositionComponent.class);
		DirectionComponent direction = agent.getComponent(DirectionComponent.class);
		
		...
	}
}
```

**Komponent**

```java
public class FoodComponent extends Component{
	private final int energy;
	private final int growthTime;
	
	private boolean harvested;
	private int deltaTime;
	private int turnTimeHarvested;
	
	// constructors
	
	...
  
	// overrides
	
	@Override
	public void onSignal(Action signal){
		if(signal.getClass() == FoodHarvested.class){
			turnTimeHarvested = ((FoodHarvested)signal).getTime();
			harvested = true;
			deltaTime = 0;
			Simulation.getSimulationEventEmitter().send(SimulationEvent.foodHarvested());
		}
		else if(signal.getClass() == FoodRegrown.class){
			harvested = false;
			Simulation.getSimulationEventEmitter().send(SimulationEvent.foodRegrown());
		}
	}
	
	...
	
	// getters and setters
	...
}
```

## Animacja

Jedzenie jest reprezentowane przez zielone, nie zjedzone, i żółte, zjedzone, koła.

Blobki są domyślnie niebieskie, jednak zmieniają swój kolor w trakcie wykonywania
niektórych operacji. Skanowanie/wąchanie - czerwony, skręcanie - jasnozielony, skanowanie
i ruch/jedzenei - jasny niebieski.

Parametry wyświetlania (np. czas wyświetlania jednej klatki, wielkość kół) aktualnie można
jedynie zmienić w src/zad1/simulation/config/GraphicSettings.java

Z powodu na sposób wyświetlania animacji, czasem może się zdarzyć, że wizualnie pewien
blob wejdzie na pole z jedzeniem i go nie zje, a chwilę później inny blob wejdzie na to
samo pole i zje jedzenie. Jest to spowodowane tym, że blobki wykonują swoje programy jeden
po drugim, a animacja jest odtwarzana jednocześnie.