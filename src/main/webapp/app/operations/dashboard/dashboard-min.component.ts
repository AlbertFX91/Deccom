import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { EventService } from '../event/event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
// import { Chart } from 'chart.js';
import 'chartjs-plugin-annotation';

import * as $ from 'jquery';
import { CV } from '../cv/cv.model';

@Component({
    selector: 'jhi-dashboard-min',
    templateUrl: './dashboard-min.component.html',
    styleUrls: ['./dashboard.component.css']
}) export class DashboardMinComponent implements OnInit, OnDestroy {

    @Input()
    cvCard: CV;

    chartType: string;
    CVs: any[];
    chartOptions: any;

    interval: string;

    constructor(
        public cvService: CVService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.CVs = [];
        // this.init();
    }

    ngOnInit() {
        this.init();
    }

    ngOnDestroy() { }


    init() {
        // console.log(this.cvCard);
        this.chartType = 'scatter';
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    id: 'x-axis-0',
                    display: true,
                    type: 'time',
                    time: {
                        unit: "day",
                        min: this.dateToNumber(new Date(new Date().getTime() - (7 * 24 * 60 * 60 * 1000))),
                        unitStepSize: 1,
                        displayFormats: {
                            day: 'll'
                        }
                    },
                    ticks: {
                        display: false
                    }
                }],
                yAxes: [{
                    id: 'y-axis-0',
                    type: 'logarithmic',
                    display: true,
                    min: 0,
                    ticks: {
                        display: false,
                        beginAtZero: true
                    }
                }]
            },
            tooltips: {
                callbacks: {
                    title: function(tooltipItem, data) {
                        // Removing milliseconds from the xLabel
                        let s_date = tooltipItem[0].xLabel;
                        const i = s_date.lastIndexOf('.');
                        const j = s_date.lastIndexOf(' ');

                        // Constructing the new date string
                        s_date = s_date.substring(0, i) + s_date.substring(j, s_date.length);
                        const showDate = new Date(s_date).toUTCString();

                        return showDate.substring(0, showDate.length - 4);
                    },
                    label: function(tooltipItem, data) {
                        let label = '' + data.datasets[tooltipItem.datasetIndex].label || '';

                        if (label) {
                            label += ': ';
                        }

                        label += tooltipItem.yLabel;
                        return label;
                    }
                }
            },
        };
        
        const dataToInsert = [];
        for (let j = 0; j < this.cvCard.controlVarEntries.length; j++) {
            const date: Date = new Date(this.cvCard.controlVarEntries[j].creationMoment);
            dataToInsert.push({
                x: this.dateToNumber(date),
                y: this.cvCard.controlVarEntries[j].value
            });
        }
        
        const color = this.cvCard.extractor.style.backgroundColor;
        const dato: any = {
            data: dataToInsert,
            label: this.cvCard.name,
            backgroundColor: color,
            borderColor: color,
            borderWidth: '2px',
            fill: false,
            showLine: true
        }
        this.CVs.push(dato);
    }

    private dateToNumber(date: Date) {
        date.setHours(date.getHours() + 2);
        return date.getTime();
    }
}
