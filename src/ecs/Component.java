package ecs;

import simulation.interfaces.Copyable;
import simulation.interfaces.Creatable;
import simulation.interfaces.Destroyable;
import simulation.interfaces.Loggable;

public interface Component extends Copyable, Creatable, Destroyable, Loggable{
	@Override
	Component copy();
}
