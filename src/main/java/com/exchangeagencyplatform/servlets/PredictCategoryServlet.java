package com.exchangeagencyplatform.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

// @WebServlet("/predictCategory")
public class PredictCategoryServlet extends HttpServlet {

  private Classifier classifier;
  private Instances dataStructure;

  @Override
  public void init() throws ServletException {
    try {
      classifier = (Classifier) SerializationHelper
          .read(getServletContext().getResourceAsStream("/WEB-INF/categorypredictnaive.model"));

      ArrayList<Attribute> attributes = new ArrayList<>();
      attributes.add(new Attribute("title", (ArrayList<String>) null));
      attributes.add(new Attribute("description", (ArrayList<String>) null));

      ArrayList<String> classValues = new ArrayList<>();
      classValues.add("Electronics");
      classValues.add("Clothing");
      attributes.add(new Attribute("category", classValues));

      dataStructure = new Instances("TestInstances", attributes, 0);
      dataStructure.setClassIndex(dataStructure.numAttributes() - 1);
    } catch (Exception e) {
      throw new ServletException("Error initializing the classifier", e);
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String itemName = request.getParameter("title");
    String description = request.getParameter("description");
    if (itemName == null || description == null) {

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

      response.getWriter().write("item_name and description parameters are required.");

      return;

    }
    double[] values = new double[dataStructure.numAttributes()];
    values[0] = dataStructure.attribute(0).addStringValue(itemName);
    values[1] = dataStructure.attribute(1).addStringValue(description);

    Instance instance = new DenseInstance(1.0, values);
    instance.setDataset(dataStructure);

    String predictedCategory;
    try {
      double result = classifier.classifyInstance(instance);
      predictedCategory = dataStructure.classAttribute().value((int) result);
    } catch (Exception e) {
      throw new ServletException("Error classifying instance", e);
    }

    Map<String, String> resultMap = new HashMap<>();
    resultMap.put("category", predictedCategory);

    response.setContentType("application/json");
    new ObjectMapper().writeValue(response.getOutputStream(), resultMap);
  }
}
