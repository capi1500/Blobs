package simulation.agents.blob.commands;

import simulation.agents.blob.components.BlobComponent;

public interface Command{
	void execute(BlobComponent blob);
}
