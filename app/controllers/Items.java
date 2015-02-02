package controllers;

import models.Item;
import models.Shop;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.BodyParser;

public class Items extends Controller {
	public static class CreateItem {
		public String name;
		public Double price;
	}

	public static Result list(Integer page) {
		return ok(views.html.list.render(Shop.INSTANCE.list()));
		//return ok(Json.toJson(shop.list()));
	}

//	@BodyParser.Of(BodyParser.Json.class)
//	public static Result create() {
//		JsonNode json = request().body().asJson();
//		CreateItem createItem;
//		try {
//			createItem = Json.fromJson(json, CreateItem.class);
//		} catch (RuntimeException e) {
//			return badRequest();
//		}
//		Item item = Shop.INSTANCE.create(createItem.name, createItem.price);
//		if (item != null) {
//			return ok(Json.toJson(item));
//		} else {
//			return internalServerError();
//		}
//	}

	@BodyParser.Of(BodyParser.FormUrlEncoded.class)
	public static Result create() {
		Form<CreateItem> submission = Form.form(CreateItem.class).bindFromRequest();
		if (submission.hasErrors()) {
			return BadRequest(views.html.createForm.render(submission));
		} else {
			CreateItem createItem = submission.get();
			Item item = Shop.INSTANCE.create(createItem.name, createItem.price);
			if (item != null) {
				return redirect(routes.Items.details(item.id));
			} else {
				return internalServerError();
			}
		}
	}

	public static Result createForm() {
		return ok(views.html.createForm.render(Form.form(CreateItem.class)));
	}

	public static Result details(Long id) {
		Item item = Shop.INSTANCE.get(id);
		if (item != null) {
			return ok(views.html.details.render(item));
			//return ok(Json.toJson(item));
		} else {
			return notFound();
		}
	}

	public static Result update(Long id) {
		JsonNode json = request().body().asJson();
		CreateItem createItem;
		try {
			createItem = Json.fromJson(json, CreateItem.class);
		} catch (RuntimeException e) {
			return badRequest();
		}
		Item item = Shop.INSTANCE.update(id, createItem.name, createItem.price);
		if (item != null) {
			return ok(Json.toJson(item));
		} else {
			return internalServerError();
		}
	}
	
	public static Result delete(Long id) {
		if (Shop.INSTANCE.delete(id)) {
			return ok();
		} else {
			return notFound();
		}
	}
}