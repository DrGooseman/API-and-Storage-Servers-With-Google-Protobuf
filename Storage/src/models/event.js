const fs = require("fs").promises;

const dataPath = "./src/data/events.json";

const getProductsFromFile = async () => {
  try {
    const fileContents = await fs.readFile(dataPath);
    return JSON.parse(fileContents);
  } catch (err) {
    return [];
  }
};

module.exports = class Event {
  constructor(plainEventObject) {
    this.timestamp = plainEventObject.timestamp;
    this.userId = plainEventObject.userId;
    this.event = plainEventObject.event;
  }

  async save() {
    const products = await getProductsFromFile();
    products.push(this);
    fs.writeFile(dataPath, JSON.stringify(products), (err) => {
      console.log(err);
    });
  }

  static async getAll() {
    return await getProductsFromFile();
  }
};
