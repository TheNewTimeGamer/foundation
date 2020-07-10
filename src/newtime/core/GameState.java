package newtime.core;

public interface GameState {

	public abstract void init(Game game);
	public abstract void tick(Game game);
	public abstract void exit(Game game);
	
}
