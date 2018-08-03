/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { MedidorDetailComponent } from '../../../../../../main/webapp/app/entities/medidor/medidor-detail.component';
import { MedidorService } from '../../../../../../main/webapp/app/entities/medidor/medidor.service';
import { Medidor } from '../../../../../../main/webapp/app/entities/medidor/medidor.model';

describe('Component Tests', () => {

    describe('Medidor Management Detail Component', () => {
        let comp: MedidorDetailComponent;
        let fixture: ComponentFixture<MedidorDetailComponent>;
        let service: MedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [MedidorDetailComponent],
                providers: [
                    MedidorService
                ]
            })
            .overrideTemplate(MedidorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedidorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Medidor(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.medidor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
