package kr1v.malilibApi.actions.actions;

import net.minecraft.client.MinecraftClient;

public class CommandAction extends Action {
	private String command;

	public CommandAction(String name, String command) {
		super(name);
		this.command = command;
	}

	@Override
	public void trigger() {
		MinecraftClient.getInstance().getNetworkHandler().sendChatCommand(this.command);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
